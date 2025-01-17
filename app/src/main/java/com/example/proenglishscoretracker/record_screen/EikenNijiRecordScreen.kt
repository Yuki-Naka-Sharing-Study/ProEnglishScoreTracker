package com.example.proenglishscoretracker.record_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proenglishscoretracker.data.EnglishInfoViewModel
import com.example.proenglishscoretracker.R
import com.example.proenglishscoretracker.ui.theme.ProEnglishScoreTrackerTheme
import java.util.Calendar

@Composable
fun EikenNijiRecordScreen(viewModel: EnglishInfoViewModel) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16_dp))
    ) {
        var selectedDate by rememberSaveable { mutableStateOf("") }
        var cseScore by rememberSaveable { mutableIntStateOf(0) }
        var speakingScore by rememberSaveable { mutableIntStateOf(0) }
        var shortSpeechScore by rememberSaveable { mutableIntStateOf(0) }
        var interactionScore by rememberSaveable { mutableIntStateOf(0) }
        var grammarAndVocabularyScore by rememberSaveable { mutableIntStateOf(0) }
        var pronunciationScore by rememberSaveable { mutableIntStateOf(0) }
        var memoText by rememberSaveable { mutableStateOf("") }

        //「ErrorText」系
        var selectedDateEmptyErrorText by rememberSaveable { mutableStateOf("") }
        var cseMaxScoreErrorText by rememberSaveable { mutableStateOf("") }
        var speakingMaxScoreErrorText by rememberSaveable { mutableStateOf("") }
        var shortSpeechMaxScoreErrorText by rememberSaveable { mutableStateOf("") }
        var interactionMaxScoreErrorText by rememberSaveable { mutableStateOf("") }
        var grammarAndVocabularyMaxScoreErrorText by rememberSaveable { mutableStateOf("") }
        var pronunciationMaxScoreErrorText by rememberSaveable { mutableStateOf("") }

        //「Error」系
        val selectedDateEmptyError = selectedDate.isEmpty()
        val cseMaxScoreError = cseScore >= 3401
        val speakingMaxScoreError = speakingScore >= 851
        val shortSpeechMaxScoreError = shortSpeechScore >= 11
        val interactionMaxScoreError = interactionScore >= 11
        val grammarAndVocabularyMaxScoreError = grammarAndVocabularyScore >= 11
        val pronunciationMaxScoreError = pronunciationScore >= 11

        Row {
            SelectDayText("")
            Spacer(modifier = Modifier.padding(end = dimensionResource(id = R.dimen.space_16_dp)))
            Column {
                SelectDatePicker(LocalContext.current) { date ->
                    selectedDate = date
                    selectedDateEmptyErrorText = ""
                }
                Text(selectedDate)
                if (selectedDate.isEmpty()) ErrorText(
                    selectedDateEmptyErrorText
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row {
            SelectGradeText("")
            DropdownMenuWithIcon()
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
            CSEScoreText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            Column {
                CseInputScoreField(
                    placeholder = stringResource(id = R.string.cse_score),
                    value = cseScore,
                    onValueChange = { cseScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_32_dp)))
            if (cseScore >= 3401) {
                ErrorText("CSEスコアは3401未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_52_dp)))
            SpeakingImageView()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            SpeakingText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            Column {
                SpeakingScoreInputField(
                    placeholder = stringResource(id = R.string.eiken_niji_speaking_score),
                    value = speakingScore,
                    onValueChange = { speakingScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_52_dp)))
            if (speakingScore >= 851) {
                ErrorText("Speakingスコアは851未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            ShortSpeechText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            Column {
                SectionOfSpeakingScoreInputField(
                    placeholder = stringResource(id = R.string.eiken_niji_short_speech),
                    value = shortSpeechScore,
                    onValueChange = { shortSpeechScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            if (shortSpeechScore >= 11) {
                ErrorText("Short Speechスコアは11未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            InteractionText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            Column {
                SectionOfSpeakingScoreInputField(
                    placeholder = stringResource(id = R.string.eiken_niji_interaction),
                    value = interactionScore,
                    onValueChange = { interactionScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            if (interactionScore >= 11) {
                ErrorText("Interactionスコアは11未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            GrammarAndVocabularyText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            Column {
                SectionOfSpeakingScoreInputField(
                    placeholder = stringResource(id = R.string.eiken_niji_grammar_and_vocabulary),
                    value = grammarAndVocabularyScore,
                    onValueChange = { grammarAndVocabularyScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            if (grammarAndVocabularyScore >= 11) {
                ErrorText("Grammar and Vocabularyスコアは11未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            PronunciationText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8_dp)))
            Column {
                SectionOfSpeakingScoreInputField(
                    placeholder = stringResource(id = R.string.eiken_niji_pronunciation),
                    value = pronunciationScore,
                    onValueChange = { pronunciationScore = it }
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_72_dp)))
            if (pronunciationScore >= 11) {
                ErrorText("Pronunciationスコアは11未満である必要があります。")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_32_dp)))
            MemoText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            MemoInputField(
                placeholder = stringResource(id = R.string.memo),
                value = memoText,
                onValueChange = { memoText = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

        val savable = selectedDate.isNotBlank() &&
                cseScore.toString().isNotBlank() &&
                speakingScore.toString().isNotBlank() &&
                shortSpeechScore.toString().isNotBlank() &&
                interactionScore.toString().isNotBlank() &&
                grammarAndVocabularyScore.toString().isNotBlank() &&
                !selectedDateEmptyError &&
                !cseMaxScoreError &&
                !speakingMaxScoreError &&
                !shortSpeechMaxScoreError &&
                !interactionMaxScoreError &&
                !grammarAndVocabularyMaxScoreError &&
                !pronunciationMaxScoreError

        var showSaved by remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SaveButton(
                    onClick = {
                        if (savable) {
                            selectedDateEmptyErrorText = ""
                            cseMaxScoreErrorText = ""
                            speakingMaxScoreErrorText = ""
                            shortSpeechMaxScoreErrorText = ""
                            interactionMaxScoreErrorText = ""
                            grammarAndVocabularyMaxScoreErrorText = ""
                            pronunciationMaxScoreErrorText = ""
                            showSaved = "記録しました。"
//                            viewModel.saveEikenNijiValues(
//                                cseScore,
//                                speakingScore,
//                                shortSpeechScore,
//                                interactionScore,
//                                grammarAndVocabularyScore,
//                                pronunciationScore,
//                                memoText
//                            )
                        } else {
                            if (selectedDateEmptyError) {
                                selectedDateEmptyErrorText = "受験日が記入されていません。"
                            }
                            if (cseMaxScoreError) {
                                cseMaxScoreErrorText = "CSEスコアは3401未満である必要があります。"
                            }
                            if (speakingMaxScoreError) {
                                speakingMaxScoreErrorText = "Speakingスコアは851未満である必要があります。"
                            }
                            if (shortSpeechMaxScoreError) {
                                shortSpeechMaxScoreErrorText = "Short Speechスコアは11未満である必要があります。"
                            }
                            if (interactionMaxScoreError) {
                                interactionMaxScoreErrorText = "Interactionスコアは11未満である必要があります。"
                            }
                            if (grammarAndVocabularyMaxScoreError) {
                                grammarAndVocabularyMaxScoreErrorText = "Grammar and Vocabularyスコアは11未満である必要があります。"
                            }
                            if (pronunciationMaxScoreError) {
                                pronunciationMaxScoreErrorText = "Pronunciationスコアは11未満である必要があります。"
                            }
                            if (!cseMaxScoreError) {
                                cseMaxScoreErrorText = ""
                            }
                            if (!speakingMaxScoreError) {
                                speakingMaxScoreErrorText = ""
                            }
                            if (!shortSpeechMaxScoreError) {
                                shortSpeechMaxScoreErrorText = ""
                            }
                            if (!interactionMaxScoreError) {
                                interactionMaxScoreErrorText = ""
                            }
                            if (!grammarAndVocabularyMaxScoreError) {
                                grammarAndVocabularyMaxScoreErrorText = ""
                            }
                            if (!pronunciationMaxScoreError) {
                                pronunciationMaxScoreErrorText= ""
                            }
                        }
                    },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
                ShowSavedText(showSaved)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EikenNijiRecordScreenPreview(
    @PreviewParameter(PreviewParameterProvider::class)
    viewModel: EnglishInfoViewModel
) {
    ProEnglishScoreTrackerTheme {
        EikenNijiRecordScreen(viewModel = viewModel)
    }
}

@Composable
private fun SelectDayText(day: String, modifier: Modifier = Modifier) {
    Text(
        text = "受験日を選択",
        modifier = modifier
    )
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
                String.format("%04d年%02d月%02d日", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        }, year, month, day
    )
    datePickerDialog.datePicker.maxDate = calendar.timeInMillis
    Button(
        onClick = { datePickerDialog.show() }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        ), shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "受験日を選択する",
            color = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectDayTextPreview() {
    ProEnglishScoreTrackerTheme {
        SelectDayText("受験日を選択")
    }
}

@Composable
private fun SelectGradeText(grade: String, modifier: Modifier = Modifier) {
    Text(
        text = "受験級を選択",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectGradeTextPreview() {
    ProEnglishScoreTrackerTheme {
        SelectGradeText("受験級を選択")
    }
}

@Composable
private fun DropdownMenuWithIcon() {
    val items = listOf("5級", "4級", "3級", "準2級", "2級", "準1級", "1級")
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    // Anchor用のBoxを導入
    Box(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(8.dp)
            ) {
                Text(
                    text = items[selectedIndex],
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 8.dp)
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropdownMenuWithIconPreview() {
    ProEnglishScoreTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DropdownMenuWithIcon()
        }
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
private fun CSEScoreText(grade: String, modifier: Modifier = Modifier) {
    Text(
        text = "CSEスコア",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CSEScoreTextPreview() {
    ProEnglishScoreTrackerTheme {
        CSEScoreText("CSEスコア")
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
fun ShortSpeechText(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Short Speech",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ShortSpeechTextPreview() {
    ProEnglishScoreTrackerTheme {
        ShortSpeechText("Short Speech")
    }
}

@Composable
fun InteractionText(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Interaction",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun InteractionTextPreview() {
    ProEnglishScoreTrackerTheme {
        InteractionText("Interaction")
    }
}

@Composable
fun GrammarAndVocabularyText(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Grammar And Vocabulary",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GrammarAndVocabularyTextPreview() {
    ProEnglishScoreTrackerTheme {
        GrammarAndVocabularyText("Grammar And Vocabulary")
    }
}

@Composable
fun PronunciationText(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Pronunciation",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PronunciationTextPreview() {
    ProEnglishScoreTrackerTheme {
        PronunciationText("Pronunciation")
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
private fun CseInputScoreField(placeholder: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (value >= 3401) ErrorText("")
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
    }
}

@Composable
private fun SpeakingScoreInputField(placeholder: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (value >= 851) ErrorText("")
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52_dp)),
            value = value.toString(),
            onValueChange = { newValue ->
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
    }
}

@Composable
private fun SectionOfSpeakingScoreInputField(placeholder: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (value >= 11) ErrorText("")
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52_dp)),
            value = value.toString(),
            onValueChange = { newValue ->
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
    }
}

@Composable
private fun MemoInputField(placeholder: String, value: String, onValueChange: (String) -> Unit = {}) {
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
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(stringResource(id = R.string.record), color = Color.White)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun SaveButtonPreview() {
//    ProEnglishScoreTrackerTheme {
//        SaveButton()
//    }
//}

@Composable
private fun ErrorText(error: String) {
    Text(
        text = error,
        fontSize = 12.sp,
        color = Color.Red,
        maxLines = 2,
    )
}

private fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
private fun ShowSavedText(saved: String) {
    Text(
        text = saved, fontSize = 16.sp, color = Color.Green
    )
}
