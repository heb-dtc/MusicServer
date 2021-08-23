package com.heb.soli.api

import com.rometools.modules.itunes.EntryInformation
import com.rometools.modules.itunes.ITunes
import com.rometools.rome.feed.synd.SyndFeed

class PodcastFeedParser {
    fun parse(feed: SyndFeed): PodcastFeed {

        val episodes = feed.entries.mapIndexed { index, entry ->
            val entryInformation = entry.getModule(ITunes.URI) as? EntryInformation
            PodcastEpisode(
                id = MediaId("${feed.link}_$index"),
                //TODO: check if this correct?
                playUri = entry.enclosures[0].url,
                entry.title,
                subtitle = entryInformation?.subtitle,
                summary = entryInformation?.summary,
                entry.uri,
                imageUrl = entryInformation?.imageUri?.toString() ?: feed.image.url,
                duration = null
            )
        }.toList()

        //TODO find a better id...
        return PodcastFeed(id = feed.link, name = feed.title, imageUrl = feed.image.url, episodes = episodes)
    }
}