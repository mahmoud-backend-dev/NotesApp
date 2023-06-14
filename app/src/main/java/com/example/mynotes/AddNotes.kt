package com.example.mynotes

import android.content.ContentValues
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_notes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNotes.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNotes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var id:Int?=0
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
        return inflater.inflate(R.layout.fragment_add_notes, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_back,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.btnBack->{
                view?.findNavController()?.navigate(R.id.myNotes)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        AddNotes.setOnClickListener {
            add_notes_event()
        }
        val title=arguments?.getString("Title")
        edit_title.setText(title)
        val des=arguments?.getString("Des")
        description.setText(des)
    }
    fun add_notes_event(){
        if((edit_title.text.toString().isEmpty()&&description.text.toString().isEmpty())
                || (edit_title.text.toString().isEmpty()&&description.text.toString().isNotEmpty())
                ||(edit_title.text.toString().isNotEmpty()&&description.text.toString().isEmpty()))
        {
            Toast.makeText(this.requireActivity(),"failed is record",Toast.LENGTH_LONG).show()
        }else {
            val title = edit_title.text.toString()
            val des = description.text.toString()
            val values = ContentValues()
            values.put("Title", title)
            values.put("Description", des)
            id = arguments?.getInt("ID")
            if (id!=0)
            {
                val db = DataBaseManger(this.requireActivity())
                val selectionArgs= arrayOf(id.toString())
                db.updataNote(values,"ID=?",selectionArgs)
                Toast.makeText(this.requireActivity(), "Record is update", Toast.LENGTH_SHORT).show()
            }else {
                val db = DataBaseManger(this.requireActivity())
                db.insertNotes(values)
                Toast.makeText(this.requireActivity(), "Record is added", Toast.LENGTH_SHORT).show()

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNotes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNotes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}