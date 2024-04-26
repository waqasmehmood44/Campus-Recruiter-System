package com.example.campusrecruitmentsystem.Adapters;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.Manifest;
import com.example.campusrecruitmentsystem.R;
import com.example.campusrecruitmentsystem.StudentTest.student_test;
import com.example.campusrecruitmentsystem.Models.Submit_Application;
import com.example.campusrecruitmentsystem.ViewHolders.std_job_app_view_holder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class std_job_app_adapter extends RecyclerView.Adapter<std_job_app_view_holder>{
    Context context;
    String key;
    ArrayList<String> testNamesList = new ArrayList<>();
    ArrayList<String> testidList = new ArrayList<>();
    DatabaseReference reference, reference1,test_list_ref,test_name_ref , reference2,reference3;

    String[] items = {"Item 1", "Item 2", "Item 3"};
    List<Submit_Application> itemList;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 700;
    int pagewidth = 492;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;
    int count = 0;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    boolean std_fragment;
    public std_job_app_adapter(Context context, List<Submit_Application> itemList,boolean std_fragment) {
        this.context = context;
        this.itemList = itemList;
        this.std_fragment = std_fragment;
    }
        @NonNull
    @Override
    public std_job_app_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new std_job_app_view_holder(LayoutInflater.from(context).inflate(R.layout.std_job_app_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull std_job_app_view_holder holder, int position) {
        reference = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
        reference1 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
        holder.std_job_name.setText(itemList.get(position).getJob_name());
        holder.std_job_salary.setText(itemList.get(position).getJob_salary());
        holder.std_job_location.setText(itemList.get(position).getJob_location());
        holder.std_job_desc.setText(itemList.get(position).getJob_desc());
        holder.std_job_cv.setText(itemList.get(position).getName());
        holder.selected_time1.setText(itemList.get(position).getStd_selected_time());

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.loginicon);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);
        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(context.getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        if (std_fragment) {
            holder.marks_layout.setVisibility(View.GONE);
            holder.recruiter_layout.setVisibility(View.GONE);
            holder.student_layout.setVisibility(View.VISIBLE);
        } else {
            holder.recruiter_layout.setVisibility(View.VISIBLE);
            holder.student_layout.setVisibility(View.GONE);
        }
        if("".equals(itemList.get(position).getTest_id())){
            holder.take_test.setVisibility(View.GONE);
        } else {
            if ("Accepted".equals(itemList.get(position).getApplication_status()) && "".equals(itemList.get(position).getCorrect_ans())) {
                holder.take_test.setVisibility(View.VISIBLE);
            } else {
                holder.take_test.setVisibility(View.GONE);
            }

        }
        holder.send_int_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailContent = "Subject: Invitation to Zoom Interview Meeting\n\n" +
                        "Dear [Student's Name],\n\n" +
                        "I hope this email finds you well. We are excited to invite you to an interview meeting via Zoom for the [Position/Program Name] opportunity. Your application stood out among many, and we are eager to learn more about you.\n\n" +
                        "Here are the details for the Zoom interview:\n\n" +
                        "Date: "+ itemList.get(position).getStd_selected_time() +"\n" +
                        "Meeting Link: [Zoom Meeting Link]\n" +
                        "Meeting ID: [Meeting ID]\n" +
                        "Passcode: [Passcode, if applicable]\n\n" +
                        "During the interview, you will have the opportunity to meet with members of our team and discuss your qualifications, experiences, and aspirations. Please come prepared to share examples that demonstrate your skills and achievements relevant to the role.\n\n" +
                        "If you have any questions or concerns or if you need to reschedule the interview, please don't hesitate to reach out to me at [Your Contact Information].\n\n" +
                        "We look forward to speaking with you soon!\n\n" +
                        "Best regards,\n\n" +
                        "[Your Name]\n" +
                        "[Your Position/Title]\n" +
                        "[Your Contact Information]";
                // define Intent object with action attribute as ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                // add three fields to intent using putExtra function
                intent.putExtra(Intent.EXTRA_SUBJECT, "Interview Meeting Invitation");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{itemList.get(position).getCurrent_user_email()});
                intent.putExtra(Intent.EXTRA_TEXT, emailContent);
                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser as Email client using createChooser function
                context.startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_SEND);
//

                // You can set subject and body of the email
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Interview Meeting Invitation");
                //intent.putExtra(Intent.EXTRA_TEXT, "Your message here");

                // Verify if there's an app to handle this intent
//                if (intent.resolveActivity(context.getPackageManager()) != null) {
//                    context.startActivity(intent);
//                }
            }
        });
        holder.take_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, student_test.class);
                intent.putExtra("Test_id",itemList.get(position).getTest_id());
                intent.putExtra("recruiter_id",itemList.get(position).getRec_id());
                intent.putExtra("student_id",itemList.get(position).getStudent_id());
                intent.putExtra("job_Id",itemList.get(position).getJob_id());
                context.startActivity(intent);
            }
        });
        holder.assign_int_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());

                // Create a dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // Inflate the custom layout
                View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_assign_int_time, null);
                // Set the inflated view to the dialog builder
                builder.setView(dialogView);

                EditText editText1 = dialogView.findViewById(R.id.edit_text1);
                EditText editText2 = dialogView.findViewById(R.id.edit_text2);
                Button save_time_options = dialogView.findViewById(R.id.save_time_options);
                // Create the dialog instance
                final AlertDialog dialog = builder.create();
                save_time_options.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Check if both EditText fields are not empty
                        String timeOption1 = editText1.getText().toString().trim();
                        String timeOption2 = editText2.getText().toString().trim();

                        if (!TextUtils.isEmpty(timeOption1) && !TextUtils.isEmpty(timeOption2)) {
                            // Both EditText fields are not empty, perform action and close dialog
                            Map<String, Object> updateTestId = new HashMap<>();
                            updateTestId.put("interview_time_option_1", timeOption1);
                            updateTestId.put("interview_time_option_2", timeOption2);
                            reference2.updateChildren(updateTestId);
                            reference3.updateChildren(updateTestId);
                            // Close the dialog
                            dialog.dismiss();
                        } else {
                            // At least one EditText field is empty, display a message to the user
                            Toast.makeText(context, "Please enter values for both time options", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // Set OnClickListener for EditText 1 to open DatePickerDialog and TimePickerDialog
                editText1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get current date and time
                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        // Create and show DatePickerDialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Save selected date
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create and show TimePickerDialog after date is set
                                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Handle the selected date and time (optional)
                                        // For example, you can update the EditText text with the selected date and time
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        editText1.setText(dateFormat.format(calendar.getTime()));
                                    }
                                }, hour, minute, false); // Set initial time to current time

                                // Show the TimePickerDialog after setting the date
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth); // Set initial date to current date

                        // Show the DatePickerDialog
                        datePickerDialog.show();
                    }
                });

                // Set OnClickListener for EditText 2 to open DatePickerDialog and TimePickerDialog (similar to EditText 1)
                editText2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get current date and time
                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        // Create and show DatePickerDialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Save selected date
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create and show TimePickerDialog after date is set
                                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Handle the selected date and time (optional)
                                        // For example, you can update the EditText text with the selected date and time
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        editText2.setText(dateFormat.format(calendar.getTime()));
                                    }
                                }, hour, minute, false); // Set initial time to current time

                                // Show the TimePickerDialog after setting the date
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth); // Set initial date to current date

                        // Show the DatePickerDialog
                        datePickerDialog.show();
                    }
                });
                // Create and show the AlertDialog
                dialog.show();


            }
        });
        holder.select_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Test");
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
                final String[] itemsArray = testNamesList.toArray(new String[testNamesList.size()]);
                builder.setItems(itemsArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedItem = testNamesList.get(which);
                        String selectedId = testidList.get(which);
                        Map<String, Object> updateTestId = new HashMap<>();
                        updateTestId.put("test_id", selectedId);
                        reference2.updateChildren(updateTestId);
                        reference3.updateChildren(updateTestId);
                        Toast.makeText(context.getApplicationContext(), "Test Assigned", Toast.LENGTH_SHORT).show();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        if("Accepted".equals(itemList.get(position).getApplication_status())){
            holder.accept_btn.setVisibility(View.GONE);
            holder.reject_btn.setVisibility(View.GONE);
            if ("".equals(itemList.get(position).getTest_id())) {
                holder.select_test.setVisibility(View.VISIBLE);
            }
        } else if("Rejected".equals(itemList.get(position).getApplication_status())){
            holder.accept_btn.setVisibility(View.GONE);
            holder.reject_btn.setVisibility(View.GONE);
            holder.select_test.setVisibility(View.GONE);
        }
        //Show Assign Interview Time Test button
        if ("Accepted".equals(itemList.get(position).getApplication_status()) && !"".equals(itemList.get(position).getTest_id()) && !"".equals(itemList.get(position).getCorrect_ans()) && "".equals(itemList.get(position).getInterview_time_option_1())) {
            holder.assign_int_time.setVisibility(View.VISIBLE);
        } else {
            holder.assign_int_time.setVisibility(View.GONE);
        }
        //Show Marks if Marks Updated and Test Attempted
        if ("".equals(itemList.get(position).getCorrect_ans())) {
            holder.marks_layout.setVisibility(View.GONE);
        } else {
            holder.marks_layout.setVisibility(View.VISIBLE);
            holder.marks.setText(itemList.get(position).getCorrect_ans()+"/"+itemList.get(position).getTotals_questions());
        }
        holder.time_option_1.setText(itemList.get(position).getInterview_time_option_1());
        holder.time_option_2.setText(itemList.get(position).getInterview_time_option_2());

        //Configure TIme
        if ("".equals(itemList.get(position).getInterview_time_option_1())){
            holder.time_option_1_layout.setVisibility(View.GONE);
            holder.time_option_2_layout.setVisibility(View.GONE);
        } else {
            holder.time_option_1_layout.setVisibility(View.VISIBLE);
            holder.time_option_2_layout.setVisibility(View.VISIBLE);
        }
        //If interview Time is selected
        if (!"".equals(itemList.get(position).getStd_selected_time())){
            holder.selected_time_layout.setVisibility(View.VISIBLE);
            holder.send_int_link.setVisibility(View.VISIBLE);
            holder.generate_offer_letter.setVisibility(View.VISIBLE);
        } else {
            holder.send_int_link.setVisibility(View.GONE);
            holder.generate_offer_letter.setVisibility(View.GONE);
            holder.selected_time_layout.setVisibility(View.GONE);
        }
        holder.generate_offer_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // Inflate the custom layout
                View dialogView = LayoutInflater.from(context).inflate(R.layout.generate_pdf_layout, null);
                // Set the inflated view to the dialog builder
                builder.setView(dialogView);
                // Get reference to the EditText in your custom layout
                // Get reference to the EditText in your custom layout
                TextView company_name = dialogView.findViewById(R.id.company_name);
                TextView job_role = dialogView.findViewById(R.id.job_role);
                TextView job_salary = dialogView.findViewById(R.id.job_salary);
                TextView joining_date = dialogView.findViewById(R.id.joining_date);
                TextView job_location = dialogView.findViewById(R.id.job_location);
                TextView contact_information = dialogView.findViewById(R.id.contact_information);
                Button generate_letter = dialogView.findViewById(R.id.generate_letter);
                // Create the dialog instance
                final AlertDialog dialog = builder.create();

                generate_letter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generatePDF(String.valueOf(contact_information.getText()) ,String.valueOf(job_location.getText()),String.valueOf(company_name.getText()) ,String.valueOf(job_role.getText()) ,String.valueOf(job_salary.getText()),String.valueOf(joining_date.getText()), itemList.get(position).getStudent_name());
                        dialog.dismiss();
                    }
                });
                joining_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get current date and time
                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        // Create and show DatePickerDialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Save selected date
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Create and show TimePickerDialog after date is set
                                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Handle the selected date and time (optional)
                                        // For example, you can update the EditText text with the selected date and time
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        joining_date.setText(dateFormat.format(calendar.getTime()));
                                    }
                                }, hour, minute, false); // Set initial time to current time

                                // Show the TimePickerDialog after setting the date
                                timePickerDialog.show();
                            }
                        }, year, month, dayOfMonth); // Set initial date to current date

                        // Show the DatePickerDialog
                        datePickerDialog.show();
                    }
                });

                // Create and show the AlertDialog
                dialog.show();

            }
        });
        if (itemList.get(position).getInterview_time_option_1().equals(itemList.get(position).getStd_selected_time())) {
            holder.radio_button_int_time1.setChecked(true);
            holder.radio_button_int_time1.setEnabled(false);
            holder.radio_button_int_time2.setEnabled(false);
        } else if (itemList.get(position).getInterview_time_option_2().equals(itemList.get(position).getStd_selected_time())) {
            holder.radio_button_int_time2.setChecked(true);
            holder.radio_button_int_time1.setEnabled(false);
            holder.radio_button_int_time2.setEnabled(false);
        }
        holder.radio_button_int_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
                holder.radio_button_int_time1.setEnabled(false);
                holder.radio_button_int_time2.setEnabled(false);
                Map<String, Object> updates = new HashMap<>();
                updates.put("std_selected_time", itemList.get(position).getInterview_time_option_1());
                reference2.updateChildren(updates);
                reference3.updateChildren(updates);
            }
        });
        holder.radio_button_int_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
                holder.radio_button_int_time1.setEnabled(false);
                holder.radio_button_int_time2.setEnabled(false);
                Map<String, Object> updates = new HashMap<>();
                updates.put("std_selected_time", itemList.get(position).getInterview_time_option_2());
                reference2.updateChildren(updates);
                reference3.updateChildren(updates);
            }
        });


        //get Tests List
        test_list_ref = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names")
                .child(itemList.get(position).getRec_id());
        test_list_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    if("Accepted".equals(itemList.get(position).getApplication_status()) || "Rejected".equals(itemList.get(position).getApplication_status())){
                        holder.create_test_button.setVisibility(View.VISIBLE);
                        holder.select_test.setVisibility(View.GONE);
                    }
                }
                testNamesList.clear();
                for (DataSnapshot appSnapshot : snapshot.getChildren()) {
                    key = appSnapshot.getKey();


                    test_name_ref = FirebaseDatabase.getInstance().getReference().child("Applicants Test Names")
                            .child(itemList.get(position).getRec_id()).child(key);

                    test_name_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();

                            // Extract the test name from the HashMap
                            String test_name = (String) data.get("test_name");
                            String test_id = (String) data.get("test_id");
                            testNamesList.add(test_name);
                            testidList.add(test_id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                testNamesList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        holder.std_job_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    if (context != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setType("application/pdf");
                        intent.setData(Uri.parse(itemList.get(clickedPosition).getUri()));
                        context.startActivity(intent);
                    }
                }
            }
        });
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
                Map<String, Object> updates = new HashMap<>();
                updates.put("application_status", "Accepted");
                reference2.updateChildren(updates);
                reference3.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.accept_btn.setVisibility(View.GONE);
                        holder.reject_btn.setVisibility(View.GONE);
                        holder.select_test.setVisibility(View.VISIBLE);
                        Toast.makeText(v.getContext(), "Application Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference2 = FirebaseDatabase.getInstance().getReference().child("Recruiter Job Applications").child(itemList.get(position).getRec_id()).child(itemList.get(position).getJob_id());
                reference3 = FirebaseDatabase.getInstance().getReference().child("Student Applications").child(itemList.get(position).getStudent_id()).child(itemList.get(position).getJob_id());
                Map<String, Object> updates = new HashMap<>();
                updates.put("application_status", "Rejected");
                reference2.updateChildren(updates);
                reference3.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.accept_btn.setVisibility(View.GONE);
                        holder.reject_btn.setVisibility(View.GONE);
                        Toast.makeText(v.getContext(), "Application Rejected", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
}

    private void generatePDF(String contact_information , String job_location,String company_name, String job_role, String job_salary, String joining_date, String student_name) {
        // Creating an object variable for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();
        // Variables for paint - "paint" is used for drawing shapes,
        // and we will use "title" for adding text in our PDF file.
        Paint title = new Paint();

        // Adding page info to our PDF file.
        // We specify pageWidth, pageHeight, and number of pages.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // Setting start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // Creating a variable for canvas from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // Adding typeface for our text.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Setting text size for the text in our PDF file.
        title.setTextSize(15);

        // Setting color of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(context, R.color.appColor));

        // Aligning text to left of PDF.
        title.setTextAlign(Paint.Align.LEFT);

        // Defining the offer letter text.
        String offerLetter =
                "Dear "+student_name+",\n\n"+
                "We are offering you the following terms:\n" +
                "Position:"+job_role+"\n" +
                "Start Date: "+joining_date+"\n" +
                "Salary: "+job_salary+"\n" +
                "Working Hours: 40 Hours per week\n" +
                "Location: "+job_location+"\n" +
                "We are confident that your skills and expertise will contribute\n"+
                "significantly to our company's success. Please review the\n"+
                "enclosed documents, including the job description and\n"+
                "benefits package, and let us know if you have any questions\n"+
                "or concerns.To accept this offer, please sign and return a\n"+
                "copy of this letter within a week. If you need any\n"+
                "accommodations or adjustments to facilitate your acceptance,\n"+
                "please let us know, and we will do our best to accommodate\n"+
                "your needs. We are excited about the possibility of you \n"+
                "joining our team and look forward to your positive response.\n\n"+
                "Sincerely,\n" +
                company_name+"\n" +
                contact_information;

        // Splitting the offer letter text into separate paragraphs based on "\n\n".
        String[] paragraphs = offerLetter.split("\n");

        // Starting Y-coordinate for drawing the paragraphs.
        float y = 100; // Adjust the starting Y-coordinate as needed for the top margin

        // Setting left and right margins
        float marginLeft = 50; // Adjust the left margin as needed
        float marginRight = pagewidth - 50; // Adjust the right margin as needed

        // Drawing each paragraph on the canvas.
                for (String paragraph : paragraphs) {
                    // Drawing the paragraph text on the canvas with left and right margins
                    canvas.drawText(paragraph, marginLeft, y, title);
                    // Incrementing Y-coordinate for the next paragraph
                    y += title.getTextSize() * 1.5f; // Adding some spacing between paragraphs
                }


        // Finishing our page.
        pdfDocument.finishPage(myPage);

        // Setting the name and path for our PDF file.
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        long timestamp = System.currentTimeMillis();

        File file = new File(path, timestamp+".pdf");

        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with writing to the file
            try {
                // Write PDF to the file
                // Create the PDF file
                pdfDocument.writeTo(new FileOutputStream(file));
                // Show success message
                Toast.makeText(context, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // Handle IO exception
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        // Closing our PDF file.
        pdfDocument.close();
    }




    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(context.getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(context.getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ((Activity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(context.getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Permission Denied.", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
