{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
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
Create a default fully qualified postgresql name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "gitlab.postgresql.fullname" -}}
{{- printf "%s-%s" .Release.Name .Values.postgresql.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified redis name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "gitlab.redis.fullname" -}}
{{- printf "%s-%s" .Release.Name .Values.redis.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}


{{/*
Template for outputing the gitlabUrl
*/}}
{{- define "gitlabUrl" -}}
{{- if .Values.gitlabUrl -}}
{{- .Values.gitlabUrl | quote -}}
{{- else -}}
{{- printf "http://%s-gitlab.%s:8005/" .Release.Name .Release.Namespace | quote -}}
{{- end -}}
{{- end -}}
