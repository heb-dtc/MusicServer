package com.heb.soli.api

import com.rometools.rome.feed.synd.SyndFeed

class PodcastFeedParser {
    fun parse(feed: SyndFeed): PodcastFeed {

        val episodes = feed.entries.map {
            PodcastEpisode(it.title, it.uri, feed.image.url)
        }.toList()
        
        return PodcastFeed(name = feed.title, imageUrl = feed.image.url, episodes = episodes)
    }
}