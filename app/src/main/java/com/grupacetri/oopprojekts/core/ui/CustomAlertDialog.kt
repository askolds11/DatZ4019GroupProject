package com.grupacetri.oopprojekts.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.grupacetri.oopprojekts.R

@Composable
fun CustomAlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    dismissButton: @Composable () -> Unit = {
        Text(
            stringResource(R.string.cancel),
            Modifier.clickable { onDismissRequest() })
    },
    title: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (visible) {
        AlertDialog(
            title = title,
            text = content,
            onDismissRequest = onDismissRequest,
            dismissButton = dismissButton,
            confirmButton = { /*currently empty*/ }
        )
    }
}