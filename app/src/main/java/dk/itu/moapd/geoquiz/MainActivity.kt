package dk.itu.moapd.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast





class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.questions_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById<TextView>(R.id.question_text_view)

        trueButton.setOnClickListener{view: View ->
            //Do something in response to click here
//            val toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_LONG)
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
            checkAnswer(true)
//alt
//            Toast.makeText(this, "Working", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
        }


        falseButton.setOnClickListener{view: View ->
            //Do something in response to click here
//            val toast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_LONG)
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
            checkAnswer(false)
        }

        nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
//            val questionTextResID = questionBank[currentIndex].textResId
//            questionTextView.setText(questionTextResID)
            updateQuestion()
        }


        //Challenge: Add a Listener to the TextView
        questionTextView.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        //Challenger: Add a Previous Button
        prevButton.setOnClickListener{
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

//        val questionTextResId = questionBank[currentIndex].textResId
//        questionTextView.setText(questionTextResId)
        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else {
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}