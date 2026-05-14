package com.example.lumifilm_semestralka.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.R
@Composable
fun SettingsScreen(navController: NavController, repository: MovieRepository) {
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(repository))
    val stats by viewModel.stats.collectAsState()
    val total = stats.watchedCount + stats.wantToWatchCount + stats.favouritesCount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D2D))
            .padding(16.dp)
    ) {
        Text(
            stringResource(R.string.set_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(stringResource(R.string.set_mojestats), style = MaterialTheme.typography.titleMedium, color = Color(0xFF03fc94))

        Spacer(modifier = Modifier.height(12.dp))

        // 3 štatistické boxy
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                stats.watchedCount to stringResource(R.string.set_pozrene),
                stats.wantToWatchCount to stringResource(R.string.set_chcem),
                stats.favouritesCount to stringResource(R.string.set_mylove)
            ).forEach { (count, label) ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D3D))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("$count", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF03fc94))
                        Text(label, style = MaterialTheme.typography.bodySmall, color = Color(0xFFAAAAAA))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Celkový počet
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3D3D))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.set_celkovofilmov), color = Color(0xFFFFFFFF))
                Text("$total", color = Color(0xFF03fc94), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))



    }
}