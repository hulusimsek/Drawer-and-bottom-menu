package com.hulusimsek.a9_sayfaaltyapilari

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hulusimsek.a9_sayfaaltyapilari.ui.theme._9_SayfaAltYapilariTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _9_SayfaAltYapilariTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DrawerSayfa(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DrawerSayfa(modifier: Modifier = Modifier) {
    val drawerItems = listOf("Bir", "İki")
    val secilenDrawerItem = remember { mutableStateOf(-1) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerOrBottom = remember {
        mutableStateOf(true)
    }


    val navigationItems = listOf("Bir", "İki")
    val secilenNavigationItem = remember {
        mutableStateOf(0)
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color.LightGray) {
                drawerItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        icon = {
                            when (item) {
                                "Bir" -> Icon(
                                    painter = painterResource(id = R.drawable.baseline_looks_one_24),
                                    contentDescription = ""
                                )

                                "İki" -> Icon(
                                    painter = painterResource(id = R.drawable.baseline_looks_two_24),
                                    contentDescription = ""
                                )
                            }
                        },
                        selected = secilenDrawerItem.value == index,
                        onClick = {
                            secilenDrawerItem.value = index
                            drawerOrBottom.value = false
                            secilenNavigationItem.value = -1
                            scope.launch { drawerState.close() }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color.Blue,
                            unselectedTextColor = Color.Gray,
                        ),
                        shape = RectangleShape // Oval köşeleri kaldırarak dikdörtgen yapıyoruz
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Başlık") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_menu_24),
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Blue,
                            titleContentColor = Color.White
                        )
                    )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (drawerOrBottom.value) {
                            if (secilenNavigationItem.value == 0) {
                                SayfaBir()
                            }
                            if (secilenNavigationItem.value == 1) {
                                SayfaIki()
                            }
                        } else {
                            when (secilenDrawerItem.value) {
                                0 -> SayfaBir()
                                1 -> SayfaIki()
                            }
                        }
                    }
                },
                bottomBar = {
                    NavigationBar(containerColor = Color.White) {
                        navigationItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = secilenNavigationItem.value == index,
                                onClick = {
                                    secilenNavigationItem.value = index
                                    drawerOrBottom.value = true
                                    secilenDrawerItem.value = -1 // Drawer seçimini sıfırla

                                },
                                label = { Text(text = item) },
                                icon = {
                                    when (item) {
                                        "Bir" -> Icon(
                                            painter = painterResource(id = R.drawable.baseline_looks_one_24),
                                            contentDescription = ""
                                        )

                                        "İki" -> Icon(
                                            painter = painterResource(id = R.drawable.baseline_looks_two_24),
                                            contentDescription = ""
                                        )
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Blue,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = Color.Blue,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = Color.Red
                                )

                            )

                        }
                    }
                }
            )
        }
    )


    val activity = (LocalContext.current as Activity)
    BackHandler(onBack = {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            activity.finish()
        }
    })
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBarSayfa(modifier: Modifier = Modifier) {
    val navigationItems = listOf("Bir", "İki")
    val secilenNavigationItem = remember {
        mutableStateOf(0)
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Başlık") })
    },
        content = {

            if (secilenNavigationItem.value == 0) {
                SayfaBir()
            }
            if (secilenNavigationItem.value == 1) {
                SayfaIki()
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = secilenNavigationItem.value == index,
                        onClick = {
                            secilenNavigationItem.value = index
                        },
                        label = { Text(text = item) },
                        icon = {
                            when (item) {
                                "Bir" -> Icon(
                                    painter = painterResource(id = R.drawable.baseline_looks_one_24),
                                    contentDescription = ""
                                )

                                "İki" -> Icon(
                                    painter = painterResource(id = R.drawable.baseline_looks_two_24),
                                    contentDescription = ""
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color.Blue,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Red
                        )

                    )

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _9_SayfaAltYapilariTheme {
        DrawerSayfa()
    }
}