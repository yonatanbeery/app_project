package com.example.yournexthome.Model

class Model private constructor(){
    val posts: MutableList<Post> = ArrayList()

    companion object {
        val instance: Model = Model()
    }

    init {
        for (i in 0 .. 20) {
            posts.add(Post("Name: $i", "bla bla $i", "", false))
        }
    }
}