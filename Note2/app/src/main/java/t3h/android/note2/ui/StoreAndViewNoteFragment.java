package t3h.android.note2.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import t3h.android.note2.R;
import t3h.android.note2.entity.Note;
import t3h.android.note2.viewmodel.NoteViewModel;

public class StoreAndViewNoteFragment extends Fragment {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 999;

    private static final int REQUEST_CODE_SELECT_IMAGE = 888;

    private final String currentTime =
            new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
    private EditText titleEdt, subtitleEdt, contentEdt;

    private TextView dateTimeText, linkText;

    private ImageView noteImage, deleteNoteImageIcon, deleteLinkIcon;

    private String selectedColor, selectedImage;

    private LinearLayout webURLLayout, addLinkLayout, deleteNoteLayout;

    private NoteViewModel noteViewModel;

    private View subtitleIndicatorView;

    private AlertDialog addUrlDialog, deleteNoteDialog;

    private Note alreadyAvailableNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_and_view_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backPressed(view);

        initElements(view);

        if (requireArguments().getBoolean("isViewOrUpdate")) {
            alreadyAvailableNote = (Note) requireArguments().get("noteInfo");
            setViewOrUpdate();
        }

        setSubtitleIndicator();

        initMiscellaneous(view);

        deleteSelectedImage();

        deleteLink();

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        onSaveClickListener(view);
    }

    private void initElements(View view) {
        titleEdt = view.findViewById(R.id.titleEdt);
        dateTimeText = view.findViewById(R.id.dateTimeText);
        dateTimeText.setText(currentTime);
        subtitleEdt = view.findViewById(R.id.subtitleEdt);
        noteImage = view.findViewById(R.id.noteImage);
        deleteNoteImageIcon = view.findViewById(R.id.deleteNoteImageIcon);
        webURLLayout = view.findViewById(R.id.webURLLayout);
        linkText = view.findViewById(R.id.linkText);
        deleteLinkIcon = view.findViewById(R.id.deleteLinkIcon);
        contentEdt = view.findViewById(R.id.contentEdt);
        selectedColor = "#333333";
        subtitleIndicatorView = view.findViewById(R.id.subtitleIndicatorView);
        addLinkLayout = view.findViewById(R.id.addLinkLayout);
    }

    private void setViewOrUpdate() {
        titleEdt.setText(alreadyAvailableNote.getTitle());
        dateTimeText.setText(alreadyAvailableNote.getDateTime());
        subtitleEdt.setText(alreadyAvailableNote.getSubtitle());
        contentEdt.setText(alreadyAvailableNote.getContent());

        String getImagePathFromDB = alreadyAvailableNote.getImagePath();
        if (getImagePathFromDB != null && !getImagePathFromDB.isEmpty()) {
            noteImage.setImageBitmap(BitmapFactory.decodeFile(getImagePathFromDB));
            noteImage.setVisibility(View.VISIBLE);
            deleteNoteImageIcon.setVisibility(View.VISIBLE);
            selectedImage = getImagePathFromDB;
        }

        String getLinkFromDB = alreadyAvailableNote.getWebLink();
        if (getLinkFromDB != null && !getLinkFromDB.isEmpty()) {
            linkText.setText(getLinkFromDB);
            webURLLayout.setVisibility(View.VISIBLE);
        }
    }

    private void backPressed(View view) {
        ImageView backIcon = view.findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private boolean isInvalidData(Note note) {
        if (note.getTitle().isEmpty()) {
            Toast.makeText(requireActivity(), "Note title must not be empty!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (note.getContent().isEmpty()) {
            Toast.makeText(requireActivity(), "Note content must not be empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private Note getInputData() {
        Note note = new Note();

        if (alreadyAvailableNote != null) {
            note.setId(alreadyAvailableNote.getId());
        }

        note.setTitle(titleEdt.getText().toString().trim());
        note.setDateTime(currentTime);
        note.setSubtitle(subtitleEdt.getText().toString().trim());
        note.setContent(contentEdt.getText().toString().trim());
        note.setColor(selectedColor);
        note.setImagePath(selectedImage);

        if (webURLLayout.getVisibility() == View.VISIBLE) {
            note.setWebLink(linkText.getText().toString());
        }

        return note;
    }

    private void initMiscellaneous(View view) {
        LinearLayout miscellaneousLayout = view.findViewById(R.id.miscellaneousLayout);
        BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(miscellaneousLayout);
        miscellaneousLayout.findViewById(R.id.miscellaneousText).setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        LinearLayout noteColorLayout = miscellaneousLayout.findViewById(R.id.noteColorLayout);

        ImageView imageColor1 = miscellaneousLayout.findViewById(R.id.imageColor1);
        ImageView imageColor2 = miscellaneousLayout.findViewById(R.id.imageColor2);
        ImageView imageColor3 = miscellaneousLayout.findViewById(R.id.imageColor3);
        ImageView imageColor4 = miscellaneousLayout.findViewById(R.id.imageColor4);
        ImageView imageColor5 = miscellaneousLayout.findViewById(R.id.imageColor5);

        noteColorLayout.findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectedColor = "#333333";
            imageColor1.setImageResource(R.drawable.ic_done);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicator();
        });

        noteColorLayout.findViewById(R.id.viewColor2).setOnClickListener(v -> {
            selectedColor = "#FDBE3B";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_done);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicator();
        });

        noteColorLayout.findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectedColor = "#ff4842";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_done);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            setSubtitleIndicator();
        });

        noteColorLayout.findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectedColor = "#3a52fc";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_done);
            imageColor5.setImageResource(0);
            setSubtitleIndicator();
        });

        noteColorLayout.findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectedColor = "#000000";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_done);
            setSubtitleIndicator();
        });

        miscellaneousLayout.findViewById(R.id.addImageLayout).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        999);
            } else {
                selectImage();
            }
        });

        miscellaneousLayout.findViewById(R.id.addLinkLayout).setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            showAddUrlDialog(view);
        });

        if (alreadyAvailableNote != null) {
            String getColorCodeFromDB = alreadyAvailableNote.getColor();
            if (getColorCodeFromDB != null && !getColorCodeFromDB.isEmpty()) {
                switch (getColorCodeFromDB) {
                    case "#FDBE3B":
                        miscellaneousLayout.findViewById(R.id.viewColor2).performClick();
                        break;
                    case "#ff4842":
                        miscellaneousLayout.findViewById(R.id.viewColor3).performClick();
                        break;
                    case "#3a52fc":
                        miscellaneousLayout.findViewById(R.id.viewColor4).performClick();
                        break;
                    case "#000000":
                        miscellaneousLayout.findViewById(R.id.viewColor5).performClick();
                        break;
                }
            }

            deleteNoteLayout = miscellaneousLayout.findViewById(R.id.deleteNoteLayout);
            deleteNoteLayout.setVisibility(View.VISIBLE);
            deleteNoteLayout.setOnClickListener(v -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showDeleteNoteDialog(v);
            });
        }
    }

    private void setSubtitleIndicator() {
        GradientDrawable gradientDrawable = (GradientDrawable) subtitleIndicatorView.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedColor));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(requireActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                Log.d("ImageUri", selectedImageUri + "");
                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream =
                                requireActivity().getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        noteImage.setImageBitmap(bitmap);
                        noteImage.setVisibility(View.VISIBLE);
                        deleteNoteImageIcon.setVisibility(View.VISIBLE);

                        // convert uri to string path
                        selectedImage = getPathFromUri(selectedImageUri);
                        Log.d("ImagePathString", selectedImage);
                    } catch (Exception ex) {
                        Toast.makeText(requireActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    // logic
    private String getPathFromUri(Uri selectedImageUri) {
        String filePath;
        Cursor cursor = requireActivity().getContentResolver()
                .query(selectedImageUri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(index);
            cursor.close();
        } else {
            filePath = selectedImageUri.getPath();
        }
        return filePath;
    }

    private void showAddUrlDialog(View v) {
        if (addUrlDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            View view = LayoutInflater.from(requireActivity()).inflate(
                    R.layout.add_url_layout, v.findViewById(R.id.addUrlContainerLayout)
            );
            builder.setView(view);

            addUrlDialog = builder.create();
            if (addUrlDialog.getWindow() != null) {
                addUrlDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            EditText urlEdt = view.findViewById(R.id.urlEdt);
            urlEdt.requestFocus();

            view.findViewById(R.id.textAdd).setOnClickListener(s -> {
                if (urlEdt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireActivity(), "Please enter url", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.WEB_URL.matcher(urlEdt.getText().toString()).matches()) {
                    Toast.makeText(requireActivity(), "Please enter valid url", Toast.LENGTH_SHORT).show();
                } else {
                    linkText.setText(urlEdt.getText().toString());
                    webURLLayout.setVisibility(View.VISIBLE);
                    addUrlDialog.dismiss();
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(s -> addUrlDialog.dismiss());
        }

        addUrlDialog.show();
    }

    private void showDeleteNoteDialog(View v) {
        if (deleteNoteDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            View view = LayoutInflater.from(requireActivity()).inflate(
                    R.layout.delete_note_layout, v.findViewById(R.id.deleteNoteContainerLayout)
            );
            builder.setView(view);

            deleteNoteDialog = builder.create();
            if (deleteNoteDialog.getWindow() != null) {
                deleteNoteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            view.findViewById(R.id.textDelete).setOnClickListener(td -> {
                noteViewModel.deleteNote(alreadyAvailableNote);
                deleteNoteDialog.dismiss();
                requireActivity().onBackPressed();
            });

            view.findViewById(R.id.textCancel).setOnClickListener(tc -> deleteNoteDialog.dismiss());
        }
        deleteNoteDialog.show();
    }

    private void deleteSelectedImage() {
        deleteNoteImageIcon.setOnClickListener(v -> {
            selectedImage = null;
            noteImage.setImageBitmap(null);
            noteImage.setVisibility(View.GONE);
            deleteNoteImageIcon.setVisibility(View.GONE);
        });
    }

    private void deleteLink() {
        deleteLinkIcon.setOnClickListener(v -> {
            linkText.setText(null);
            webURLLayout.setVisibility(View.GONE);
        });
    }

    private void onSaveClickListener(View view) {
        ImageView saveNoteIcon = view.findViewById(R.id.doneIcon);
        saveNoteIcon.setOnClickListener(v -> {
            if (!isInvalidData(getInputData())) {
                noteViewModel.saveNote(getInputData());
                requireActivity().onBackPressed();
            }
        });
    }
}