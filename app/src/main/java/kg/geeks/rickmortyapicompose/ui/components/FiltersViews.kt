import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.geeks.rickmortyapicompose.data.dto.CharacterFilter
import kg.geeks.rickmortyapicompose.data.dto.EpisodeFilter
import kg.geeks.rickmortyapicompose.data.dto.LocationFilter
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String, onSearchTextChanged: (String) -> Unit, onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            label = { Text("Поиск по имени", fontSize = 20.sp) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray, focusedLabelColor = DarkGray
            )
        )
        Button(
            onClick = onFilterClick,
            modifier = Modifier.height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Фильтры")
        }
    }
}

@Composable
fun CharacterFilterDialog(
    currentFilter: CharacterFilter,
    onApply: (CharacterFilter) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentFilter.status ?: "Любой") }
    var selectedSpecies by remember { mutableStateOf(currentFilter.species ?: "Любой") }
    var selectedGender by remember { mutableStateOf(currentFilter.gender ?: "Любой") }

    val statusList = listOf("Любой", "Alive", "Dead", "unknown")
    val speciesList = listOf(
        "Любой",
        "Human",
        "Alien",
        "Mythological",
        "Robot",
        "Animal",
        "Disease",
        "Cronenberg",
        "Humanoid",
        "unknown"
    )
    val genderList = listOf("Любой", "Male", "Female", "Genderless", "unknown")

    AlertDialog(containerColor = DarkGray, onDismissRequest = onDismiss, title = {
        Text(
            "Фильтры", fontSize = 20.sp, color = White, fontWeight = FontWeight.Bold
        )
    }, text = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Статус:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(statusList) { status ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                        ) {
                            RadioButton(
                                selected = selectedStatus == status,
                                onClick = { selectedStatus = status },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = White, unselectedColor = White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = status, fontSize = 16.sp, color = White)
                    }
                }
            }

            // Вид
            Text("Вид:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(speciesList) { species ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                        ) {
                            RadioButton(
                                selected = selectedSpecies == species,
                                onClick = { selectedSpecies = species },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = White, unselectedColor = White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = species, fontSize = 16.sp, color = White)
                    }
                }
            }

            // Пол
            Text("Пол:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(genderList) { gender ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                        ) {
                            RadioButton(
                                selected = selectedGender == gender,
                                onClick = { selectedGender = gender },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = White, unselectedColor = White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = gender, fontSize = 16.sp, color = White)
                    }
                }
            }
        }
    }, confirmButton = {
        Button(
            onClick = {
                onApply(
                    currentFilter.copy(
                        status = if (selectedStatus == "Любой") null else selectedStatus,
                        species = if (selectedSpecies == "Любой") null else selectedSpecies,
                        gender = if (selectedGender == "Любой") null else selectedGender
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGray, contentColor = Black
            )
        ) {
            Text("Применить", fontSize = 22.sp, color = Black)
        }
    }, dismissButton = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onReset,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(46.dp)
                    .padding(end = 8.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGray, contentColor = Black
                )
            ) {
                Text("Сбросить", fontSize = 18.sp, color = Black)
            }
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(46.dp)
                    .padding(start = 8.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGray, contentColor = Black
                )
            ) {
                Text("Отмена", fontSize = 18.sp, color = Black)
            }
        }
    })
}

@Composable
fun EpisodeFilterDialog(
    currentFilter: EpisodeFilter,
    onApply: (EpisodeFilter) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {

    //Решил сделать по сезонам потому что так лучше
    var selectedEpisode by remember { mutableStateOf(currentFilter.episode ?: "Любой") }
    val episodes = listOf("Любой", "S01", "S02", "S03", "S04", "S05")

    AlertDialog(
        containerColor = DarkGray,
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Фильтр", fontSize = 20.sp, color = White, fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Сезон:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(episodes) { episode ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                            ) {
                                RadioButton(
                                    selected = selectedEpisode == episode,
                                    onClick = { selectedEpisode = episode },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = White, unselectedColor = White
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = episode, fontSize = 16.sp, color = White)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onApply(currentFilter.copy(episode = if (selectedEpisode == "Любой") null else selectedEpisode))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGray, contentColor = Black
                )
            ) {
                Text("Применить", fontSize = 22.sp, color = Black)
            }
        },
        dismissButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onReset,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(46.dp)
                        .padding(end = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray, contentColor = Black
                    )
                ) {
                    Text("Сбросить", fontSize = 18.sp, color = Black)
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(46.dp)
                        .padding(start = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray, contentColor = Black
                    )
                ) {
                    Text("Отмена", fontSize = 18.sp, color = Black)
                }
            }
        }
    )
}


@Composable
fun LocationFilterDialog(
    currentFilter: LocationFilter,
    onApply: (LocationFilter) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    var selectedType by remember { mutableStateOf(currentFilter.type ?: "Любой") }
    var selectedDimension by remember { mutableStateOf(currentFilter.dimension ?: "Любая") }

    val types = listOf("Любой", "Planet", "Cluster", "Space station", "Microverse", "Unknown")
    val dimensions =
        listOf(
            "Любая",
            "C-137",
            "Replacement",
            "Cronenberg",
            "Fantasy",
            "Post-Apocalyptic",
            "Unknown"
        )

    AlertDialog(
        containerColor = DarkGray,
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Фильтр по локации", fontSize = 20.sp, color = White, fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Тип:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(types) { type ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                            ) {
                                RadioButton(
                                    selected = selectedType == type,
                                    onClick = { selectedType = type },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = White, unselectedColor = White
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = type, fontSize = 16.sp, color = White)
                        }
                    }
                }

                Text("Измерение:", fontSize = 18.sp, color = White, fontWeight = FontWeight.Bold)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(dimensions) { dimension ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                            ) {
                                RadioButton(
                                    selected = selectedDimension == dimension,
                                    onClick = { selectedDimension = dimension },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = White, unselectedColor = White
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = dimension, fontSize = 16.sp, color = White)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onApply(
                        currentFilter.copy(
                            type = if (selectedType == "Любой") null else selectedType,
                            dimension = if (selectedDimension == "Любая") null else selectedDimension
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGray, contentColor = Black
                )
            ) {
                Text("Применить", fontSize = 22.sp, color = Black)
            }
        },
        dismissButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onReset,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(46.dp)
                        .padding(end = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray, contentColor = Black
                    )
                ) {
                    Text("Сбросить", fontSize = 18.sp, color = Black)
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(46.dp)
                        .padding(start = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray, contentColor = Black
                    )
                ) {
                    Text("Отмена", fontSize = 18.sp, color = Black)
                }
            }
        }
    )
}

