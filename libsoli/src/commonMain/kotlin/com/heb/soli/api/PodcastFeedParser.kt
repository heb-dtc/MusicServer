package com.heb.soli.api

expect class PodcastFeedParser() {
    fun parse(podcastXmlFeed: String) : PodcastFeed
}