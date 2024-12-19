package com.example.proenglishscoretracker.record_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proenglishscoretracker.data.EnglishInfoViewModel
import com.example.proenglishscoretracker.R
import com.example.proenglishscoretracker.ui.theme.ProEnglishScoreTrackerTheme
import java.util.Calendar

@Composable
fun ToeicSwRecordScreen(viewModel: EnglishInfoViewModel) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16_dp))
    ) {
        var selectedDate by rememberSaveable { mutableStateOf("") }
        var writingScore by rememberSaveable { mutableIntStateOf(0) }
        var speakingScore by rememberSaveable { mutableIntStateOf(0) }
        var memoText by rememberSaveable { mutableStateOf("") }

        Row {
            SelectDayText("")
            Spacer(modifier = Modifier.padding(end = dimensionResource(id = R.dimen.space_24_dp)))
            Column {
                SelectDatePicker(LocalContext.current) { date->
                    selectedDate = date
                }
                Text("受験日: $selectedDate")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row {
            EnterScoreText("")
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_32_dp)))
            WritingText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            WritingImageView()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            InputScoreRow(
                placeholder = stringResource(id = R.string.toeic_reading_score),
                value = writingScore,
                onValueChange = { writingScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_32_dp)))
            SpeakingText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            SpeakingImageView()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            InputScoreRow(
                placeholder = stringResource(id = R.string.toeic_listening_score),
                value = speakingScore,
                onValueChange = { speakingScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_32_dp)))
            MemoText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            InputMemoRow(
                placeholder = stringResource(id = R.string.memo),
                value = memoText,
                onValueChange = { memoText = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        // 問題点① 受験日とメモを記入していない段階でエラーメッセージを表示すると
        // 　　　　 ユーザーからすると鬱陶しいと思われるかも。
        val selectedDateEmptyError = selectedDate.isEmpty()
        val writingMaxScoreError = writingScore >= 496
        val speakingMaxScoreError = speakingScore >= 496
        val writingScoreDivisionError = writingScore % 5 != 0
        val speakingScoreDivisionError = speakingScore % 5 != 0
        val memoEmptyError = memoText.isEmpty()

        val enableChecker = !selectedDateEmptyError &&
                writingScore.toString().isNotBlank() &&
                speakingScore.toString().isNotBlank() &&
                !memoEmptyError &&
                !writingMaxScoreError &&
                !speakingMaxScoreError &&
                !writingScoreDivisionError &&
                !speakingScoreDivisionError

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SaveButton(
                onClick = {
                    viewModel.saveToeicSwValues(
                        writingScore,
                        speakingScore,
                        memoText
                    )
                },
                errorMessage = when {
                    selectedDateEmptyError -> "受験日が記入されていません。"
                    writingMaxScoreError -> "スコアは496未満である必要があります。"
                    speakingMaxScoreError -> "スコアは496未満である必要があります。"
                    writingScoreDivisionError -> "スコアは5で割り切れる必要があります。"
                    speakingScoreDivisionError -> "スコアは5で割り切れる必要があります。"
                    memoEmptyError -> "メモが記入されていません。"
                    else -> {
                        ""
                    }
                },
                enabled = enableChecker
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToeicSwScreenPreview(
    @PreviewParameter(PreviewParameterProvider::class)
    viewModel: EnglishInfoViewModel
) {
    ProEnglishScoreTrackerTheme {
        ToeicSwRecordScreen(viewModel = viewModel)
    }
}

@Composable
private fun SelectDayText(day: String, modifier: Modifier = Modifier) {
    Text(
        text = "受験日を選択",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectDayTextPreview() {
    ProEnglishScoreTrackerTheme {
        SelectDayText("受験日を選択")
    }
}

@Composable
private fun SelectDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate =
                String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        }, year, month, day
    )
    datePickerDialog.datePicker.maxDate = calendar.timeInMillis
    Button(onClick = { datePickerDialog.show() }, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Blue
    ), shape = RoundedCornerShape(8.dp)) {
        Text(
            text = "受験日を選択する",
            color = Color.White,
        )
    }
}

@Composable
private fun EnterScoreText(grade: String, modifier: Modifier = Modifier) {
    Text(
        text = "スコアを記入",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun EnterScoreTextPreview() {
    ProEnglishScoreTrackerTheme {
        EnterScoreText("スコアを記入")
    }
}

@Composable
private fun WritingText(writingText: String, modifier: Modifier = Modifier) {
    Text(
        text = "Writing",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun WritingTextPreview() {
    ProEnglishScoreTrackerTheme {
        WritingText("Writing")
    }
}

@Composable
private fun WritingImageView(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.writing),
        contentDescription = "",
        modifier = modifier
            .size((dimensionResource(id = R.dimen.space_32_dp)))
            .aspectRatio(1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun WritingImageViewPreview() {
    ProEnglishScoreTrackerTheme {
        WritingImageView(modifier = Modifier)
    }
}

@Composable
private fun SpeakingText(speakingText: String, modifier: Modifier = Modifier) {
    Text(
        text = "Speaking",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SpeakingTextPreview() {
    ProEnglishScoreTrackerTheme {
        SpeakingText("Speaking")
    }
}

@Composable
private fun SpeakingImageView(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.speaking),
        contentDescription = "",
        modifier = modifier
            .size((dimensionResource(id = R.dimen.space_32_dp)))
            .aspectRatio(1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun SpeakingImageViewPreview() {
    ProEnglishScoreTrackerTheme {
        SpeakingImageView(modifier = Modifier)
    }
}

@Composable
private fun MemoText(memoText: String, modifier: Modifier = Modifier) {
    Text(
        text = "Memo",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun MemoTextPreview() {
    ProEnglishScoreTrackerTheme {
        MemoText("Memo")
    }
}

@Composable
private fun MemoTextField(modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52_dp)),
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = { Text("MEMO") },
            placeholder = { Text("") },
            maxLines = 5,
            singleLine = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoTextFieldPreview() {
    ProEnglishScoreTrackerTheme {
        MemoTextField(modifier = Modifier)
    }
}

@Composable
private fun InputScoreRow(placeholder: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52_dp)),
            value = value.toString(),
            onValueChange = { newValue ->
                // 数字のみ受け付ける
                if (newValue.all { it.isDigit() }) {
                    onValueChange(newValue.toInt())
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.space_16_sp).value.sp),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
    }
}

@Composable
private fun InputMemoRow(placeholder: String, value: String, onValueChange: (String) -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52_dp)),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.space_16_sp).value.sp),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
    }
}


@Composable
private fun SaveButton(
    onClick: () -> Unit = {},
    errorMessage: String,
    enabled: Boolean = true
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showToast(context, "記録しました") },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
        ) {
            Text(stringResource(id = R.string.record), color = Color.White)
        }
        Text(errorMessage, color = Color.Red)
    }
}

private fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

//@Preview(showBackground = true)
//@Composable
//private fun SaveButtonPreview() {
//    ProEnglishScoreTrackerTheme {
//        SaveButton()
//    }
//}