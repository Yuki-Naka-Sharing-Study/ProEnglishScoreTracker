package com.example.proenglishscoretracker

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proenglishscoretracker.ui.theme.ProEnglishScoreTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun EikenNijiRecordScreen(viewModel: EnglishInfoViewModel) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16))
    ) {
        var cseScore by rememberSaveable { mutableStateOf("") }
        var speakingScore by rememberSaveable { mutableStateOf("") }
        var shortSpeechScore by rememberSaveable { mutableStateOf("") }
        var interactionScore by rememberSaveable { mutableStateOf("") }
        var grammarAndVocabularyScore by rememberSaveable { mutableStateOf("") }
        var pronunciationScore by rememberSaveable { mutableStateOf("") }
        var memoText by rememberSaveable { mutableStateOf("") }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            SelectDayText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24)))
            DrumRollTypeDatePicker()
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            SelectGradeText("")
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            EnterScoreText("")
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            CSEScoreText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
            InputRow(
                placeholder = stringResource(id = R.string.cse_score),
                value = cseScore,
                onValueChange = { cseScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            SpeakingText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            SpeakingImageView()
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
            InputRow(
                placeholder = stringResource(id = R.string.eiken_niji_speaking_score),
                value = speakingScore,
                onValueChange = { speakingScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            ShortSpeechText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            InputRow(
                placeholder = stringResource(id = R.string.eiken_niji_short_speech),
                value = shortSpeechScore,
                onValueChange = { shortSpeechScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            InteractionText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            InputRow(
                placeholder = stringResource(id = R.string.eiken_niji_interaction),
                value = interactionScore,
                onValueChange = { interactionScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            GrammarAndVocabularyText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            InputRow(
                placeholder = stringResource(id = R.string.eiken_niji_grammar_and_vocabulary),
                value = grammarAndVocabularyScore,
                onValueChange = { grammarAndVocabularyScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            PronunciationText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            InputRow(
                placeholder = stringResource(id = R.string.eiken_niji_pronunciation),
                value = pronunciationScore,
                onValueChange = { pronunciationScore = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_8)))
            MemoText("")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
            InputRow(
                placeholder = stringResource(id = R.string.memo),
                value = memoText,
                onValueChange = { memoText = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16)))

        val isButtonEnabled = cseScore.isNotBlank() &&
                speakingScore.isNotBlank() &&
                shortSpeechScore.isNotBlank() &&
                interactionScore.isNotBlank() &&
                grammarAndVocabularyScore.isNotBlank() &&
                pronunciationScore.isNotBlank() &&
                memoText.isNotBlank()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SaveButton(
                onClick = {
                    viewModel.saveEikenNijiValues(
                        cseScore,
                        speakingScore,
                        shortSpeechScore,
                        interactionScore,
                        grammarAndVocabularyScore,
                        pronunciationScore,
                        memoText
                    )
                },
                enabled = isButtonEnabled
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrumRollTypeDatePicker() {
    var isPickerVisible by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("日付を選択") }

    Button(onClick = { isPickerVisible = true }) {
        Text(text = selectedDate, color = Color.White)
    }

    if (isPickerVisible) {
        DatePickerDialog(
            onDismiss = { isPickerVisible = false },
            onDateSelected = { year, month, day ->
                selectedDate = "$year 年 $month 月 $day 日"
                isPickerVisible = false
            }
        )
    }
}

@Composable
private fun DatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Int, Int, Int) -> Unit
) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (1900..2100).toList()
    val months = (1..12).toList()
    val days = (1..31).toList()

    var selectedYear by remember { mutableStateOf(currentYear) }
    var selectedMonth by remember { mutableStateOf(1) }
    var selectedDay by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { onDateSelected(selectedYear, selectedMonth, selectedDay) }) {
                    Text("確定", color = Color.White)
                }
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("日付を選択")
            }
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScrollPicker(
                    items = years,
                    selectedItem = selectedYear,
                    onItemSelected = { selectedYear = it },
                    highlightColor = Color.Red
                )
                ScrollPicker(
                    items = months,
                    selectedItem = selectedMonth,
                    onItemSelected = { selectedMonth = it },
                    highlightColor = Color.Green
                )
                ScrollPicker(
                    items = days,
                    selectedItem = selectedDay,
                    onItemSelected = { selectedDay = it },
                    highlightColor = Color.Blue
                )
            }
        }
    )
}

@Composable
private fun <T> ScrollPicker(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    highlightColor: Color
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Update selected item when scroll stops
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    val centerIndex =
                        (listState.firstVisibleItemIndex + (listState.layoutInfo.visibleItemsInfo.size / 2))
                            .coerceIn(0, items.lastIndex)
                    onItemSelected(items[centerIndex])
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(150.dp)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items.size) { index ->
            val item = items[index]
            Text(
                text = item.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                            onItemSelected(item)
                        }
                    },
                style = if (item == selectedItem) {
                    MaterialTheme.typography.bodyLarge.copy(
                        color = highlightColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                } else {
                    MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                }
            )
        }
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
            .size((dimensionResource(id = R.dimen.space_32)))
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
private fun InputRow(placeholder: String, value: String, onValueChange: (String) -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.space_52)),
            value = value,
            onValueChange = onValueChange,
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
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16)))
    }
}

@Composable
private fun SaveButton(
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    var showMessage by remember { mutableStateOf(false) }

    Button(
        onClick = { showMessage = true },
        colors = ButtonDefaults.buttonColors(Color.Blue),
        enabled = enabled
    ) {
        Text("記録する", color = Color.White)
    }

    if (showMessage) {
        Text("記録しました。")
        LaunchedEffect(Unit) {
            delay(2000)
            showMessage = false
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveButtonPreview() {
    ProEnglishScoreTrackerTheme {
        SaveButton()
    }
}