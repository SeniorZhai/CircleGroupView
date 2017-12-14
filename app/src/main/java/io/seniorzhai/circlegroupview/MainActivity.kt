package io.seniorzhai.circlegroupview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_main)
        view1.loadImage("https://vignette.wikia.nocookie.net/typemoon/images/4/40/Fate-extra_servants.jpg/revision/latest/scale-to-width-down/373?cb=20170701171903")
        view2.loadImage("http://images.goodsmile.info/cgm/images/product/20170117/6183/43256/large/ea2e27ef2e6c2d9684f91a84cb47e59a.jpg",
                "https://myanimelist.cdn-dena.com/images/characters/3/313456.jpg")
        view3.loadImage("https://www.animelab.com/blog/wp-content/uploads/2016/06/Rin-T.png",
                "https://pbs.twimg.com/media/DFl6M6HUIAADDx5.jpg",
                "https://pbs.twimg.com/profile_images/613249325645205505/aQXYRFEy.jpg")
        view4.loadImage("https://i.ytimg.com/vi/nnyNECiLJLg/maxresdefault.jpg",
                "https://www.animenewsnetwork.com/thumbnails/max550x550/cms/news/113956/grand-order.jpg",
                "https://wallpapersite.com/images/wallpapers/saber-4800x2700-fate-grand-order-hd-4k-3185.jpg",
                "http://img.youtube.com/vi/ff52-g00L-Y/maxresdefault.jpg")
    }
}
