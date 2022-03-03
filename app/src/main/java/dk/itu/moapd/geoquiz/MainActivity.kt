package dk.itu.moapd.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView


    private val quizViewModel: QuizViewModel by lazy{
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called");
        setContentView(R.layout.activity_main)


        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
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
//            currentIndex = (currentIndex + 1) % questionBank.size
//            val questionTextResID = questionBank[currentIndex].textResId
//            questionTextView.setText(questionTextResID)
            quizViewModel.moveToNext()
            updateQuestion()
        }


        //Challenge: Add a Listener to the TextView
        questionTextView.setOnClickListener{
//            currentIndex = (currentIndex + 1) % questionBank.size
            val questionTextResId = quizViewModel.currentQuestionText
            updateQuestion()
        }
        //Challenger: Add a Previous Button
        prevButton.setOnClickListener{
//            currentIndex = (currentIndex - 1) % questionBank.size
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        cheatButton.setOnClickListener{
            //Start CheatActivity
//            val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
//            startActivity(intent)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
//        val questionTextResId = questionBank[currentIndex].textResId
//        questionTextView.setText(questionTextResId)
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
    private fun updateQuestion(){
//        val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
//        val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer

//        val messageResId = if (userAnswer == correctAnswer){
//            R.string.correct_toast
//        }else {
//            R.string.incorrect_toast
//        }
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}