package com.heb.soli.api

import com.rometools.modules.itunes.EntryInformation
import com.rometools.modules.itunes.ITunes
import com.rometools.rome.feed.synd.SyndFeed

class PodcastFeedParser {
    fun parse(feed: SyndFeed): PodcastFeed {

        val episodes = feed.entries.map {
            val entryInformation = it.getModule(ITunes.URI) as? EntryInformation
            PodcastEpisode(
                id = MediaId(""),
                //TODO: check if this correct?
                playUri = it.enclosures[0].url,
                it.title,
                subtitle = entryInformation?.subtitle,
                summary = entryInformation?.summary,
                it.uri,
                imageUrl = entryInformation?.imageUri?.toString() ?: feed.image.url,
                duration = null
            )
        }.toList()

        return PodcastFeed(id = feed.uri ?: "", name = feed.title, imageUrl = feed.image.url, episodes = episodes)
    }
}