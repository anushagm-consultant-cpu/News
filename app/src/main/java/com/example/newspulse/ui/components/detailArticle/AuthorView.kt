package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyAuthorView(){
    Column() {
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                modifier = Modifier.background(color = Color.LightGray, shape = androidx.compose.foundation.shape.CircleShape).size(60.dp).padding(10.dp),
                contentDescription = null,
                tint = Color.Gray,


            )
            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(vertical = 10.dp)
            ) {
                Text(text = "Author Name", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Row (){
                    Text(text = "BBC News", fontSize = 14.sp,color = Color.Gray)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = ".",color = Color.Gray)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Oct 11 2003", fontSize = 14.sp,color = Color.Gray)
                }
            }

        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
    }
}