package com.example.shopinglistapp

import android.app.AlertDialog
import android.text.method.NumberKeyListener
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class ShopListing(
    val id:Int,
    var name:String,
    var quantity:Int,
    var Editing:Boolean=false)



@Composable
fun ShopListingAppEditing(item: ShopListing , onEditComleplete:(String,Int)-> Unit){

    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.Editing) }

Row (   modifier= Modifier
    .fillMaxWidth()
    .background(Color.White)
    .padding(8.dp),

    horizontalArrangement = Arrangement.SpaceEvenly
){
    Column {
        BasicTextField(
            value = editedName, onValueChange =
            { editedName = it },
            singleLine = true,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        )
        BasicTextField(
            value = editedQuantity, onValueChange =
            { editedQuantity = it },
            singleLine = true,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        )
    }
    Button(onClick = { isEditing = false
        onEditComleplete(editedName, editedQuantity.toIntOrNull()?:1) }
    )


    {
        Text(text = "Save")
    }


}

}



@Composable
fun ShopListingApp() {
    var SItems by remember { mutableStateOf(listOf<ShopListing>()) }
    var showDialog by remember { mutableStateOf(false) }
    var ItemName by remember { mutableStateOf("") }
    var ItemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            items(SItems) { item ->
                if (item.Editing) {
                    ShopListingAppEditing(
                        item = item,
                        onEditComleplete = { editedName, editedQuantity ->
                            // Update the list by mapping over it, setting all Editing to false, and updating the edited item
                            SItems = SItems.map {
                                if (it.id == item.id) {
                                    it.copy(name = editedName, quantity = editedQuantity, Editing = false)
                                } else {
                                    it.copy(Editing = false)
                                }
                            }
                        }
                    )
                } else {
                    ShopListingItem(
                        item = item,
                        onEditClick = {
                            SItems = SItems.map {
                                it.copy(Editing = it.id == item.id)
                            }
                        },
                        onDeleteClick = {
                            SItems = SItems - item
                        }
                    )
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (ItemName.isNotBlank() && ItemQuantity.isNotBlank()) {
                                val newItem = ShopListing(
                                    id = SItems.size + 1,
                                    name = ItemName,
                                    quantity = ItemQuantity.toIntOrNull() ?: 1
                                )
                                SItems = SItems + newItem
                                showDialog = false
                                ItemName = ""
                                ItemQuantity = ""
                            }
                        }) {
                            Text(text = "Add")
                        }
                        Button(onClick = { showDialog = false }) {
                            Text(text = "Cancel")
                        }
                    }
                },
                title = { Text(text = "Add Shopping Item") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = ItemName,
                            onValueChange = { ItemName = it },
                            modifier = Modifier.padding(8.dp),
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = ItemQuantity,
                            onValueChange = { ItemQuantity = it },
                            modifier = Modifier.padding(8.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                },
            )
        }
    }
}

@Composable
fun ShopListingItem(
    item: ShopListing,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = item.name)
            Text(text = "Adet: ${item.quantity}")
        }
        Row {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
