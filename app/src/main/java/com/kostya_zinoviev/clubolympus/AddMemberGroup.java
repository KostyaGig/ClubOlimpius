package com.kostya_zinoviev.clubolympus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import data.ClubOlimpusContract.MemberEntry;

public class AddMemberGroup extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportEditText;
    private Spinner pol;
    int genderPol = MemberEntry.KEY_UNKNOWN;
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_group);
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        sportEditText = findViewById(R.id.sport);
        pol = findViewById(R.id.spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.spinnerAdapter,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pol.setAdapter(spinnerAdapter);
        pol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //метод onItemSelected срабатывает,когда с нашим спинером происходят действия,тоесть что-то выбирают
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Переменная,которая переводитсяя как Выбранный пол
                //Мы знаем,что в нашем спинере хранятся значения типа Srtring,поэтому кастим
                String selectedGender = (String)parent.getItemAtPosition(position);
                //Выше мы ловим наше значение и помещаем в переменую и проверяем
                //Если переменная не пуста,т.е выбрано какое-то значение,то выполняем что-то
                if(!TextUtils.isEmpty(selectedGender)){
                    //Т.К переменная не пуста и содержит какое - то значение,провеярем какое это значение
                    switch (selectedGender){
                        //Если это неизвестный присваиваем нашей переменной значение 0,с помощью которой мы определим какой это пол
                        case "Unknown":
                            genderPol = MemberEntry.KEY_UNKNOWN;
                            break;
                        case "Male":
                            genderPol = MemberEntry.KEY_MALE;
                            break;
                        case "Female":
                            genderPol = MemberEntry.KEY_FEMALE;
                            break;
                    }
                }
            }
                //метод  onNothingSelected срабатывает,когда с нашим спинером ничего не происходит,ничего не выбирают)
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Если ничего не выбирают,то это Unknown,неизвестный пол,следовательно переменная равна 0
                genderPol = MemberEntry.KEY_UNKNOWN;
            }
        });
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteMember:
                return true;
            /*break;*/
            case R.id.saveMember:
                insertMember();
                return true;
            /*break;*/
            case android.R.id.home:
                //По нажатию на ButonHome мы будем возвращаться на предыдущую активити,с помощью следующего кода:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            /*break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu,menu);
        return true;
    }

    // создадим Метод,который будет добавлять наших людей
    private void insertMember(){
        //Получаем значения полей из наших EdiText
        //Метод trim() обрезает все пробелы в начале и в конце строки
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportEditText.getText().toString().trim();

        ContentValues cv = new ContentValues();
        cv.put(MemberEntry.COLUMN_FIRST_NAME,firstName);
        cv.put(MemberEntry.COLUMN_LAST_NAME,lastName);
        cv.put(MemberEntry.COLUMN_SPORT,sport);
        cv.put(MemberEntry.COLUMN_GENDER,genderPol);
        ContentResolver cr = getContentResolver();
        Uri uri  = cr.insert(MemberEntry.CONTENT_URI,cv);
        if(uri == null){
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
    }
}
