package com.example.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DataBaseManger {
    val data_base_name="MyNotes"
    val data_base_tableNotes="Notes"
    val calId="ID"
    val calTitle="Title"
    val calDes="Description"
    val data_base_version=1
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+data_base_tableNotes+ " ("+calId+" INTEGER PRIMARY KEY, "+
            calTitle+" TEXT, "+calDes+" TEXT);"
    var sqlDB:SQLiteDatabase?=null
    constructor(context: Context) {
    var data_base = DataBaseHelperNotes(context)
        sqlDB=data_base.writableDatabase
    }
    inner class DataBaseHelperNotes:SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,data_base_name,null,data_base_version)
        {
            this.context=context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(sqlCreateTable)
            Toast.makeText(context," CREATE DATA BASE",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("Drop table IF EXISTS "+data_base_tableNotes)
        }
    }

    fun insertNotes(values:ContentValues):Long{
        var id =sqlDB!!.insert(data_base_tableNotes,null,values)
        return id
    }
    fun query(projection :Array<String>,selection:String,selectionArgs:Array<String>,sortOrder:String):Cursor{
        val db=SQLiteQueryBuilder()
        db.tables=data_base_tableNotes
        val cursor=db.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder)
        return cursor
    }
    fun delete(selection:String,selectionArgs:Array<String>):Int
    {
        val count =sqlDB!!.delete(data_base_tableNotes,selection,selectionArgs)
        return count
    }

    fun updataNote(value:ContentValues,selection: String,selectionArgs: Array<String>):Int
    {
        val count =sqlDB!!.update(data_base_tableNotes,value,selection,selectionArgs)
        return count
    }
}