package io.seniorzhai.circlegroupview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_main)
//        view1.loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276489&di=38df214b0408a3c3b8a43a148a42a594&imgtype=jpg&er=1&src=http%3A%2F%2Fhimg2.huanqiu.com%2Fattachment2010%2F2017%2F0122%2F20170122093516442.jpg")
//        view2.loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276511&di=18e74c39a650f4dd1ddd06da6172d1e7&imgtype=jpg&er=1&src=http%3A%2F%2Fmingxing.facang.com%2Fuploads%2Fallimg%2F150623%2F16334W910-2.jpg",
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276554&di=b3b11669f532dd70674b3dec44f54445&imgtype=jpg&er=1&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201202%2F07%2F20120207170814_GjZm3.jpg")
//        view3.loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276559&di=7a00c5954f67354fd4baa3bc26df5822&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201411%2F16%2F20141116200735_yYA5e.jpeg",
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276596&di=8e6f08a08c6f80235d096e469e37738d&imgtype=jpg&er=1&src=http%3A%2F%2Fww1.sinaimg.cn%2Fbmiddle%2F7dc89805gw1drgr4461vzj.jpg",
//                "https://pbs.twimg.com/profile_images/613249325645205505/aQXYRFEy.jpg")
//        view4.loadImage("https://i.ytimg.com/vi/nnyNECiLJLg/maxresdefault.jpg",
//                "https://www.animenewsnetwork.com/thumbnails/max550x550/cms/news/113956/grand-order.jpg",
//                "https://wallpapersite.com/images/wallpapers/saber-4800x2700-fate-grand-order-hd-4k-3185.jpg",
//                "http://img.youtube.com/vi/ff52-g00L-Y/maxresdefault.jpg")
        Glide.with(this)
                .asBitmap()
                .apply(RequestOptions.bitmapTransform(MultiTransformation(CenterCrop(), FanCrop(0,this))))
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514276129&di=9a3c1590511e89e80085f116861a6c71&imgtype=jpg&er=1&src=http%3A%2F%2Fc2.biketo.com%2Fd%2Ffile%2Fnews%2Finternational%2F2017-10-27%2F426cdd5aacb072846aa6f2699a0f26e7.jpg")
                .into(image)
    }
}
