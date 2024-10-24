package org.d3ifcool.tempconv.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3ifcool.tempconv.R

@Composable
fun MainScreen(){
    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            // Header Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "tempConv",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.refresh_icon), contentDescription = "")
                }
            }

            // Weather Card
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4D6DE3)
                ),
                shape = RoundedCornerShape(40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 12.dp)
                        .sizeIn(minHeight = 200.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Weather Icon and Text (Hujan Pagi)
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .sizeIn(maxWidth = 100.dp)
                                    .padding(bottom = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.cuaca),
                                    contentDescription = "Cuaca Hujan",
                                    modifier = Modifier.aspectRatio(1f)
                                )
                            }
                            Text(
                                "Hujan\nPagi",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        // Temperature and Date
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 80.sp,
                                            fontFamily = FontFamily(Font(R.font.kanitbold))
                                        )
                                    ) {
                                        append("30")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 70.sp,
                                            fontFamily = FontFamily(Font(R.font.kanitblack))
                                        )
                                    ) {
                                        append("°")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 40.sp,
                                            fontFamily = FontFamily(Font(R.font.kanitbold)),
                                            baselineShift = BaselineShift(0.25f)
                                        )
                                    ) {
                                        append("C")
                                    }
                                },
                                fontSize = 80.sp,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.background,
                            )
                            Text(
                                "15 Oktober\n2021",
                                fontSize = 20.sp,
                                color = Color.White,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Temperature Button
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.temp_button),
                        contentDescription = "Temperature",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Home Button
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.home_button),
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Settings Button
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.setting_button),
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
