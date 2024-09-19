package main

import (
    "fmt"
    "net/http"
)

/**
 * Einfacher Webserver der unter localhost:8080 "Hello!" zurückgibt.
 * Programm mit 'go run hello.go' starten.
 */
func main() {
    http.HandleFunc("/", HelloServer)
    http.ListenAndServe(":8080", nil)
}

func HelloServer(w http.ResponseWriter, r *http.Request) {
    fmt.Fprint(w, "Hello!")
}
