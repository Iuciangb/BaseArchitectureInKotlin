package com.yy.basearchitectureinkotlin.api.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author YY
 * @create 2022/12/9
 * @Describe
 **/
data class CheckYourself(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("age")
    val age: Int = 0,
    @SerializedName("height")
    val height: Float = 0f,
    @SerializedName("weight")
    val weight: Float = 0f
) {
    override fun toString(): String {
        return "CheckYourself(name=$name, age=$age, height=$height, weight=$weight)"
    }
}