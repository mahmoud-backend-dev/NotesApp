package com.example.mynotes

class note {
    var noteId : Int?=null
    var noteTitle:String?=null
    var noteDes:String?=null
    constructor(noteId : Int, noteTitle:String, noteDes:String){
        this.noteId=noteId
        this.noteTitle=noteTitle
        this.noteDes=noteDes
    }
}