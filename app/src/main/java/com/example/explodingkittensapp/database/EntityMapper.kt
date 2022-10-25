package com.example.explodingkittensapp.database

interface EntityMapper<T,V>  {
    fun mapFromCached(type: T): V

    fun mapToCached(type: V): T
}