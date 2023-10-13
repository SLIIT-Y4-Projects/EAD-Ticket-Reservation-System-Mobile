package com.example.ead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import com.example.ead.R;
import com.example.ead.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context) {
        this.context = context;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        if (studentList != null && position < studentList.size()) {
            Student student = studentList.get(position);
            holder.nameTextView.setText("Name: "+student.getName());
            holder.ageTextView.setText("Age: " + student.getAge());
            holder.genderTextView.setText("Gender: " + student.getGender());

            // Add bindings for the new buttons
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Delete the student from the database
                    // ...
                    String studentIdToDelete = student.getId();

                    // Create an Intent to start the DeleteStudentActivity
                    Intent intent = new Intent(context, DeleteStudentActivity.class);
                    intent.putExtra("studentId", studentIdToDelete);

                    // Start the DeleteStudentActivity
                    context.startActivity(intent);
                }
            });

            // ...
            holder.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the selected student's ID
                    String studentIdToUpdate = student.getId();
                    String studentName = student.getName();
                    String studentGender = student.getGender();
                    int studentAge = student.getAge();


                    // Create an Intent to start the UpdateStudentActivity and pass the student ID
                    Intent intent = new Intent(context, UpdateStudentActivity.class);
                    intent.putExtra("studentId", studentIdToUpdate);
                    intent.putExtra("name", studentName);
                    intent.putExtra("age", studentAge);
                    intent.putExtra("gender", studentGender);


                    // Start the UpdateStudentActivity
                    context.startActivity(intent);
                }
            });
// ...

        }
    }

    @Override
    public int getItemCount() {
        return studentList != null ? studentList.size() : 0;
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;

        TextView genderTextView;

        Button deleteButton;
        Button updateButton;

        StudentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);

            genderTextView = itemView.findViewById(R.id.genderTextView);

            // Add bindings for the new buttons
            deleteButton = itemView.findViewById(R.id.deleteButton);
            updateButton = itemView.findViewById(R.id.updateButton);
        }
    }
}
