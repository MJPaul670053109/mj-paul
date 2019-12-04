package com.mj.plugin

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project


class RemoteExtension {


    NamedDomainObjectContainer<Domain> domain

    String str

    String aa

    // 多平台复用对应的每个平台代码所在目录，另一个作用时打成maven库时，作为库名称后缀用，不同的平台打的maven库是不一样的
    String bb

    RemoteExtension() {}

    RemoteExtension(Project project) {
        NamedDomainObjectContainer<Domain> domainObjs = project.container(Domain)
        domain = domainObjs
    }

    void domains(Action<NamedDomainObjectContainer<Domain>> action) {
        action.execute(domain)
    }
}
