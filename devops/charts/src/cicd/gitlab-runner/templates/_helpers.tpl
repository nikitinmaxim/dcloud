{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "proxy" -}}
{{- printf "http://%s:%v" .host .port -}}
{{- end -}}

{{- define "noproxy" -}}
{{- range $index, $value := . -}}
{{- if gt $index 0 -}}
{{ "," }}
{{- end -}}
{{ $value }}
{{- end -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "fullname" -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Template for outputing the gitlabUrl
*/}}
{{- define "gitlabUrl" -}}
{{- .Values.gitlabUrl | quote -}}
{{- end -}}
