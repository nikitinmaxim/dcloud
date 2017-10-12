package main

import (
	"net/http"
	"flag"
	"encoding/json"
	"strconv"
	"strings"
	"github.com/xanzy/go-gitlab"
	"log"
	"os"
	"fmt"
	"net/url"
)

func flatten(inf map[string]interface{}) (ret map[string]string) {
	ret = make(map[string]string)
	for k,v := range inf {
		for nk, nv := range process(k, v) {
			ret[nk] = nv
		}
	}
	return
}
func process(k string, v interface{}) (ret map[string]string) {
	ret = make(map[string]string)
	switch t := v.(type) {
	case bool:
		ret[k] = strconv.FormatBool(t)
	case float64:
		ret[k] = strconv.FormatFloat(t, 'g', -1, 64)
	case string:
		ret[k] = t
	case nil:
		ret[k] = "null"
	case []interface{}:
		for i, nv := range t {
			for nk, nv := range process(k + "[" + strconv.FormatUint(uint64(i), 10) + "]", nv) {
				ret[nk] = nv
			}
		}
	case map[string]interface{}:
		for nk, nv := range flatten(t) {
			ret[k + "." + nk] = nv
		}
	}
	return
}

func tovars(joinmap map[string]string) string {
	list := make([]string, len(joinmap))
	i := 0
	for k, v := range joinmap {
		list[i] = "variables[" + url.QueryEscape(k) + "]=" + url.QueryEscape(v)
		i++
	}
	return strings.Join(list, "&")
}

func advanceFunc(client *gitlab.Client, advance string, debug bool) func (writer http.ResponseWriter, request *http.Request) {
	advanceRefs := strings.Split(advance, ",")

	return func (writer http.ResponseWriter, request *http.Request) {
		token := request.Header.Get("X-Gitlab-Token")

		var body map[string]interface{}
		dec := json.NewDecoder(request.Body)
		dec.Decode(&body)

		flat := flatten(body)

		if flat["object_kind"] != "pipeline" || flat["object_attributes.status"] != "success" {
			writer.WriteHeader(http.StatusOK)
			return
		}

		if debug {
			fmt.Println(flat)
		}

		rawref := flat["object_attributes.ref"]

		ref := ""
		for _, v := range advanceRefs {
			if rawref == v {
				ref = v
				break
			}
		}

		if ref == "" {
			writer.WriteHeader(http.StatusOK)
			return
		}

		source := flat["project.path_with_namespace"]

		last := strings.LastIndex(source, "/")
		targetRoot := source[:last] + "/root"
		sourceProj := source[last+1:]

		var roots []*gitlab.Project

		rootStr := "root"
		page := 1
		for {
			ps, resp, err := client.Projects.ListProjects(&gitlab.ListProjectsOptions{ListOptions: gitlab.ListOptions{Page: page, PerPage: 100}, Search: &rootStr})
			roots = append(roots, ps...)
			if err != nil {
				log.Println(ps, resp, err)
				writer.WriteHeader(http.StatusInternalServerError)
				return
			}
			if page == resp.LastPage {
				break
			}
		}

		var root *gitlab.Project
		for _, r := range roots {
			if r.PathWithNamespace == targetRoot {
				root = r
				break
			}
		}

		if root == nil {
			log.Printf("Target root %s not found", targetRoot)
			writer.WriteHeader(http.StatusInternalServerError)
			return
		}

		joinmap := make(map[string]string)
		joinmap["SUBMODULE"] = sourceProj
		joinmap["SUBMODULE_URL"] = flat["project.git_http_url"]

		join := tovars(joinmap)
		log.Printf("Advancing project %s(%d) at ref %s with token %s\n", targetRoot, root.ID, ref, token)
		http.Post(client.BaseURL().String() + "/projects/" + strconv.FormatUint(uint64(root.ID), 10) + "/ref/" + url.PathEscape(ref) + "/trigger/pipeline?token=" + token + "&" + join, "", nil)
		writer.WriteHeader(http.StatusOK)
	}
}

func main() {
	listenPtr := flag.String("listen", ":8080", "Listen on address")
	gitlabPtr := flag.String("gitlab", "", "GitLab http url")
	tokenPtr := flag.String("token", "", "Gitlab user private token")
	advancePtr := flag.String("advance-refs", "master,testing", "Advancing refs")
	debugPtr := flag.Bool("debug", false, "Debug output")
	flag.Parse()

	parseErr := false
	if *gitlabPtr == "" {
		parseErr = true
		fmt.Println("-gitlab is required; e.g. http://127.0.0.1/api/v4")
	}

	if *tokenPtr == "" {
		parseErr = true
		fmt.Println("-token is required")
	}

	if parseErr {
		if flag.NFlag() == 0 {
			flag.PrintDefaults()
		}
		os.Exit(1)
	}

	git := gitlab.NewClient(nil, *tokenPtr)
	git.SetBaseURL(*gitlabPtr)

	http.HandleFunc("/advance", advanceFunc(git, *advancePtr, *debugPtr))
	http.ListenAndServe(*listenPtr, nil)
}
