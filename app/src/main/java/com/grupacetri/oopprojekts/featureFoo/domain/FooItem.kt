package com.grupacetri.oopprojekts.featureFoo.domain

import com.grupacetri.oopprojekts.Foo

data class FooItem(
    val fooOrig: Long?,
    val foo: String?
) {
    fun toFoo(): Foo {
        return Foo(fooOrig)
    }
}

fun Foo.toFooItem(): FooItem {
    return FooItem(this.foo, this.foo?.toString() + "ooga booga")
}