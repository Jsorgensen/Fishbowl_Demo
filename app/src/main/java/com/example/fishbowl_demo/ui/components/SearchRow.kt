package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.ui.theme.roboRegular


@Composable
fun SearchRow(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.grey_100))
            .padding(horizontal = 10.dp)
            .padding(bottom = 15.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f),
            value = searchText,
            placeholder = { Text(
                text = stringResource(R.string.search_jokes),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = roboRegular,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_field_border),
                )
            ) },
            onValueChange = onSearchTextChange,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = roboRegular,
                fontWeight = FontWeight.Bold,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.text_field_border),
                unfocusedBorderColor = colorResource(R.color.text_field_border),
                cursorColor = colorResource(R.color.text_field_border),
            ),
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
                .clickable { onSearchClick() },
            text = stringResource(R.string.search),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = roboRegular,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}