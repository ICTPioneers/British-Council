package com.example.british_council

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var txt: TextView? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initID()
        txt?.text = "پادکست British Council براساس سطح زبان آموز از پایه (Beginner – A1) تا پیشرفته (Advanced – C1) طبقه بندی شده است. هر قسمت از این پادکست در مورد موقعیت یا موضوعی روزمره است و شامل تمرین هایی است که به تقویت مهارت شنیدن کمک زیادی می کند.\n" +
                "\n" +
                "پادکست British Council با تمرکز بر روی مهارت شنیدن (listening) و تلفظ (pronunciation) طراحی شده است. هرچه مهارت شنیدن خود را تقویت کنید درک شما از زبان انگلیسی بالاتر خواهد رفت.\n" +
                "\n" +
                "دروس خودآموز این بخش طبق چارچوب مرجع مشترک اروپایی برای زبان ها\u200C\u200C یا همان CEFR نوشته شده است. در این چارچوب زبان آموزان به سه دسته کلی در شش مرحله تقسیم می\u200Cشوند که در هر سطح مشخص می\u200Cشود در آن سطح از چه مهارت\u200Cهایی در حوزه چهار مهارت اصلی خواندن، نوشتن، صحبت کردن و شنیدار برخوردار است.\n" +
                "\n" +
                "نحوه ی سطح بندی CEFR:\n" +
                "\n" +
                "کاربر پایه: سطح A1 – کاربر پایه: سطح A2 – کاربر مستقل: سطح B1 – کاربر مستقل: سطح B2 – کاربر ماهر: سطح C1 – کاربر حرفه\u200Cای: سطح C2\n" +
                "\n" +
                "شما می توانید با توجه به سطح زبان خود از قسمت های مختلف این پادکست استفاده کنید و مهارت شنیدن خود را بیش از پیش تقویت کنید.\n" +
                "\n" +
                "افرادی که در این پادکست صحبت می کنند دارای ملیت های مختلف هستند و زبان انگلیسی مورد استفاده آن ها دقیقا زبانی است که در حال حاضر در دنیای واقعی به کار می رود.\n" +
                "\n"


    }

    private fun initID() {
        txt = findViewById(R.id.dis_main)
    }
}