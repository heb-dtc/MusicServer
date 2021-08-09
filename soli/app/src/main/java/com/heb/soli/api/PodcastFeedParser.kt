package com.heb.soli.api

import com.rometools.rome.feed.synd.SyndFeed

class PodcastFeedParser {
    fun parse(feed: SyndFeed): PodcastFeed {
        return PodcastFeed(name = feed.title, imageUrl =  feed.image.url , episodes = emptyList())
    }
}