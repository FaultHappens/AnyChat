package com.example.anychat.presentation.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentProfileBinding
import com.example.anychat.presentation.vm.ProfileFragmentVM
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.*


class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    private val vm: ProfileFragmentVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.userDTOLiveData.observe(viewLifecycleOwner) {
            binding.nameET.text = it.username
            val strBuilder = StringBuilder()
            if(it.firstname != null) {
                strBuilder.append(it.firstname)
                strBuilder.append(" ")
            }
            if(it.lastname != null){
                strBuilder.append(it.lastname)
            }
            if(strBuilder.isNotEmpty()){
                binding.fullnameET.text = strBuilder.toString()
            }
            val profile = it.profile


            if(profile != null){
                vm.getPhoto( it.profile.split("/")[1])
            }


            binding.descriptionET.text = it.about ?: "No Description"

        }
        val username = context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.getString("username", null)


          vm.userPhotoLiveData.observe(viewLifecycleOwner){
              binding.userImageIV.setImageBitmap(it)
            }

        binding.editBttn.setOnClickListener {
            findNavController().navigate(R.id.profileEditFragment)
        }
        binding.chatBtn.setOnClickListener {
            findNavController().navigate(R.id.chatFragment)
        }
        binding.logoutBtn.setOnClickListener {
            context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
            findNavController().navigate(R.id.loginFragment)
        }



        binding.userImageIV.setOnClickListener {
          dispatchTakePictureIntent()
        }

        if(username == null)
            findNavController().navigate(R.id.loginFragment)
        else
           vm.getUser(username)

    }
    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.userImageIV.setImageBitmap(imageBitmap)

            //create a file to write bitmap data
            val f =  File(context?.cacheDir, UUID.randomUUID().toString());
            f.createNewFile();


            val bitmap = imageBitmap
            val bos =  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            val bitmapdata = bos.toByteArray()


            val fos = FileOutputStream(f)
            fos.use {
                fos.write(bitmapdata);
                fos.flush()
                fos.close()
            }



            val reqFile = RequestBody.create(MediaType.parse("image/jpg"), f)
            val body = MultipartBody.Part.createFormData("file", f.name, reqFile)
            vm.uploadPhoto(body)
        }
    }

}