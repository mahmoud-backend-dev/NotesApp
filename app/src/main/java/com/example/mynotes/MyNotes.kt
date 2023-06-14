package com.example.mynotes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_notes.view.*
import kotlinx.android.synthetic.main.fragment_my_notes.*
import kotlinx.android.synthetic.main.note_ticket.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyNotes.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyNotes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var list_notes=ArrayList<note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        //list_notes.add(note(1, " meet professor", "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."))
        //list_notes.add(note(2, " meet doctor", "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."))
        //list_notes.add(note(3, " meet friend", "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."))
        querySearch("%")
    }
    fun querySearch(noteTitle:String)
    {
        var dbManger=DataBaseManger(this.requireActivity())
        val projection= arrayOf("ID","Title","Description")
        val selectionArgs= arrayOf(noteTitle)
        val cursor=dbManger.query(projection,"Title like ?",selectionArgs,"Title")
        if (cursor.moveToFirst())
        {
            list_notes.clear()
            do {
                val id=cursor.getInt(cursor.getColumnIndex("ID"))
                val title=cursor.getString(cursor.getColumnIndex("Title"))
                val des=cursor.getString(cursor.getColumnIndex("Description"))
                list_notes.add(note(id,title,des))
            }while (cursor.moveToNext())
        }
        var my_adapter=my_notes_adapter(list_notes,this.requireActivity())
        lvNotes.adapter=my_adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.btnAddNotes->{
                view?.findNavController()?.navigate(R.id.action_myNotes_to_addNotes)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class my_notes_adapter:BaseAdapter{
        var list_notes_adapter=ArrayList<note>()
        var context:Context?=null
        constructor(list_notes_adapter:ArrayList<note>, context:Context)
        {
            this.list_notes_adapter=list_notes_adapter
            this.context=context
        }

        override fun getCount(): Int {
            return list_notes_adapter.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.note_ticket,null)
            var note=list_notes_adapter[position]
            myView.tvTitle.text=note.noteTitle
            myView.tvDes.text=note.noteDes
            myView.imDel.setOnClickListener {
                val dbManger=DataBaseManger(this.context!!)
                val selectionArgs= arrayOf(note.noteId.toString())
                dbManger.delete("ID=?",selectionArgs)
                querySearch("%")
            }
            myView.imEdit.setOnClickListener {
                go_on_updata_note(note)
            }
            return myView
        }
    }
    fun go_on_updata_note(note:note)
    {
        val bun=Bundle()
        bun.putInt("ID",note.noteId!!)
        bun.putString("Title",note.noteTitle)
        bun.putString("Des",note.noteDes)
        view?.findNavController()?.navigate(R.id.action_myNotes_to_addNotes,bun)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyNotes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyNotes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}