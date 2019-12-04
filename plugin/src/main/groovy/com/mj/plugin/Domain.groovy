package com.mj.plugin;

class Domain {

    //必须定义一个 name 属性，并且这个属性值初始化以后不要修改
    String name

    String msg

    Domain(String name) {
        this.name = name
    }

    void msg(String msg) {
        this.msg = msg
    }
}