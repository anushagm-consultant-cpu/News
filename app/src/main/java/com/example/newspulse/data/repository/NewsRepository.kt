package com.example.newspulse.data.repository

import com.example.newspulse.R
import com.example.newspulse.data.NewsItem

class NewsRepository {

    fun fectchNews(): List<NewsItem> {
        return listOf(
            NewsItem(
                "Market Update",
                "Global markets react to new digital currency regulations",
                R.drawable.img_1,
                content = "Btcoin has oncan ce again asserted its dominance in the global financial landscape, transcending its initial identity as a mere digital currency to become a foundational asset class for institutional investors. As we move further into 2024, the ripples of its recent technological upgrades and the stabilizing regulatory environment are creating a new paradigm for how the world perceives value and decentralized trust.\n" +
                        "\n" +
                        "The narrative surrounding cryptocurrency is shifting from speculative volatility toward strategic asset allocation. Financial institutions that once remained on the sidelines are now integrating blockchain-based assets into their diversified portfolios, citing Bitcoin's role as a potential hedge against traditional market fluctuations and inflationary pressures.\n" +
                        "\n" +
                        "\"Bitcoin is no longer an experiment; it is the benchmark for the next generation of financial infrastructure.\"\n" +
                        "\n" +
                        "Industry experts point to the \"halving\" events and the introduction of spot ETFs as critical catalysts. These mechanisms have not only reduced the available supply but have also democratized access for millions of retail investors who prefer the security of traditional brokerage accounts over direct wallet management.\n" +
                        "\n" +
                        "However, the road ahead is not without challenges. Sustainability remains a central theme in the discourse, with increased pressure on mining operations to transition toward renewable energy sources. The intersection of environmental responsibility and digital scarcity will likely define the next chapter of the Bitcoin story."
            ),
            NewsItem(
                "Tech News",
                "New AI models are changing the way we write code forever.",
                R.drawable.img_2,
                content = "Btcoin has oncan ce again asserted its dominance in the global financial landscape, transcending its initial identity as a mere digital currency to become a foundational asset class for institutional investors. As we move further into 2024, the ripples of its recent technological upgrades and the stabilizing regulatory environment are creating a new paradigm for how the world perceives value and decentralized trust.\n" +
                        "\n" +
                        "The narrative surrounding cryptocurrency is shifting from speculative volatility toward strategic asset allocation. Financial institutions that once remained on the sidelines are now integrating blockchain-based assets into their diversified portfolios, citing Bitcoin's role as a potential hedge against traditional market fluctuations and inflationary pressures.\n" +
                        "\n" +
                        "\"Bitcoin is no longer an experiment; it is the benchmark for the next generation of financial infrastructure.\"\n" +
                        "\n" +
                        "Industry experts point to the \"halving\" events and the introduction of spot ETFs as critical catalysts. These mechanisms have not only reduced the available supply but have also democratized access for millions of retail investors who prefer the security of traditional brokerage accounts over direct wallet management.\n" +
                        "\n" +
                        "However, the road ahead is not without challenges. Sustainability remains a central theme in the discourse, with increased pressure on mining operations to transition toward renewable energy sources. The intersection of environmental responsibility and digital scarcity will likely define the next chapter of the Bitcoin story."

            ),
            NewsItem(
                "Sports",
                "Local team wins championship in a thrilling overtime finish.",
                R.drawable.img_3,
                content = "Btcoin has oncan ce again asserted its dominance in the global financial landscape, transcending its initial identity as a mere digital currency to become a foundational asset class for institutional investors. As we move further into 2024, the ripples of its recent technological upgrades and the stabilizing regulatory environment are creating a new paradigm for how the world perceives value and decentralized trust.\n" +
                        "\n" +
                        "The narrative surrounding cryptocurrency is shifting from speculative volatility toward strategic asset allocation. Financial institutions that once remained on the sidelines are now integrating blockchain-based assets into their diversified portfolios, citing Bitcoin's role as a potential hedge against traditional market fluctuations and inflationary pressures.\n" +
                        "\n" +
                        "\"Bitcoin is no longer an experiment; it is the benchmark for the next generation of financial infrastructure.\"\n" +
                        "\n" +
                        "Industry experts point to the \"halving\" events and the introduction of spot ETFs as critical catalysts. These mechanisms have not only reduced the available supply but have also democratized access for millions of retail investors who prefer the security of traditional brokerage accounts over direct wallet management.\n" +
                        "\n" +
                        "However, the road ahead is not without challenges. Sustainability remains a central theme in the discourse, with increased pressure on mining operations to transition toward renewable energy sources. The intersection of environmental responsibility and digital scarcity will likely define the next chapter of the Bitcoin story."

            )
        )
    }


}