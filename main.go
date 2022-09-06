package main

import (
	"encoding/json"
	"flag"
	"log"
	"net/http"
	"os"

	"github.com/PuerkitoBio/goquery"
)

const (
	defaultUrl = ""
	shortDesc  = "an url to be scrape"
)

type image struct {
	Urls []string `json:"urls"`
}

func main() {
	url := flag.String("url", defaultUrl, shortDesc)

	flag.Parse()

	if *url == "" {
		log.Fatal("-url flag is required")
	}

	res, err := http.Get(*url)
	if err != nil {
		log.Fatal(err)
	}

	defer func(res *http.Response) {
		res.Body.Close()
	}(res)

	if res.StatusCode != http.StatusOK {
		log.Fatalf("status code error, want %d, expected %d", http.StatusOK, res.StatusCode)
	}

	doc, err := goquery.NewDocumentFromReader(res.Body)

	var img image

	doc.Find(".post-body").Children().Filter(".separator").Each(func(i int, s *goquery.Selection) {
		imageLink, ok := s.Find("a").Attr("href")
		if ok {
			img.Urls = append(img.Urls, imageLink)
		}
	})

	result, err := json.MarshalIndent(img, "", "\t")
	if err != nil {
		log.Print(err)
	}

	if err := os.WriteFile("result.json", result, os.ModePerm); err != nil {
		log.Fatal(err)
	}

	log.Println("Successfully get the image urls")
}
