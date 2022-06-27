package com.example.anychat.presentation.ui.fragment

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.ChangeImageDialogBinding
import com.example.anychat.databinding.FragmentProfileBinding
import com.example.anychat.presentation.vm.ProfileFragmentVM
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ProfileFragment : Fragment() {

    private var SELECT_PICTURE = 1
    private val REQUEST_IMAGE_CAPTURE = 2


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
        val profileUsernameFromChat = arguments?.getString("profileUsername")
        val username = if(profileUsernameFromChat != null){
               binding.editBttn.visibility =  View.INVISIBLE
               binding.linearLayout4.visibility = View.INVISIBLE
              binding.linearLayout3.visibility = View.INVISIBLE

              profileUsernameFromChat
        }
        else{
            context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.getString("username", null)


        }




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
            showImageDialog()
        }

        if(username == null)
            findNavController().navigate(R.id.loginFragment)
        else
           vm.getUser(username)

    }

    private fun showImageDialog() {

        val dialogBinding: ChangeImageDialogBinding = ChangeImageDialogBinding.inflate(LayoutInflater.from(requireContext()))


        val dialog = Dialog(requireContext())

        dialogBinding.chooseImageBttn.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
            dialog.cancel()
        }
        dialogBinding.takePhotoBttn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
            dialog.cancel()
        }

        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val imageUri = data?.data
                if(imageUri != null) {
                    val imageStream = context?.contentResolver?.openInputStream(imageUri);
                    val  selectedImage = BitmapFactory.decodeStream(imageStream)
                    binding.userImageIV.setImageBitmap(selectedImage)
                    uploadPhoto(selectedImage)
                }
            }else if(requestCode == REQUEST_IMAGE_CAPTURE){
                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.userImageIV.setImageBitmap(imageBitmap)

                uploadPhoto(imageBitmap)

            }
        }
    }

    private fun uploadPhoto(imageBitmap: Bitmap) {
        //create a file to write bitmap data
        val f = File(context?.cacheDir, UUID.randomUUID().toString());
        f.createNewFile();


        val bitmap = imageBitmap
        val bos = ByteArrayOutputStream();
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