package org.sugr.gearshift.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.sugr.gearshift.Logger
import org.sugr.gearshift.R
import org.sugr.gearshift.model.Profile
import org.sugr.gearshift.viewmodel.ProfileEditorViewModel.Consumer

class ProfileEditorViewModelTest {
    val log = mock<Logger> {
        on { E(any<String>(), any()) }.then { println(it.arguments[0]) }
    }

    var res = mock<Resources> {
        on { getStringArray(R.array.pref_update_interval_entries) } doReturn arrayOf("1", "2", "15", "30")
        on { getIntArray(R.array.pref_update_interval_values) } doReturn intArrayOf(1, 2, 15, 30)
    }


    val ctx = mock<Context> {
        on { resources } doReturn res
    }

    val pref = mock<SharedPreferences> {
        on { getString("profile_invalid_1", null) } doReturn """{"id": "invalid_1"}"""
        on { getString("profile_invalid_2", null) } doReturn """{"id": "invalid_2", "name":"foo", "host": "http://sugr.org", "port": 12345, "proxyHost": "http://example.com"}"""
        on { getString("profile_valid_1", null) } doReturn """{"id": "valid_1", "name":"foo", "host": "http://sugr.org", "port": 12345}"""
    }

    @Test
    fun canLeaveInvalid() {
        val vmInvalid = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile(id = "invalid_1"))

        // Wait for the validators to debounce
        Thread.sleep(400)

        assertThat("Invalid profiles can't leave", !vmInvalid.canLeave())
    }

    @Test
    fun canLeaveValid() {
        val vmValid = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile(id = "valid_1"))

        // Wait for the validators to debounce
        Thread.sleep(400)

        assertThat("Valid profiles can leave", vmValid.canLeave())
    }

    @Test
    fun check() {

    }

    @Test
    fun onPickUpdateInterval() {
        val vm = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile())
        val consumer = object : Consumer {
            override fun showUpdateIntervalPicker(current: Int): Single<Int> {
                return Single.just(current + 15)
            }

        }

        vm.updateIntervalValue.set(15)

        vm.bind(consumer)

        vm.onPickUpdateInterval()

        Thread.sleep(100)

        assertThat("30", `is`(vm.updateIntervalLabel.get()))
        assertThat(30, `is`(vm.updateIntervalValue.get()))
    }

    @Test
    fun isValidEmptyProfile() {
        val vm = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile())

        // Wait for the validators to debounce
        Thread.sleep(400)

        assertThat("Empty profiles are not valid", !vm.isValid())
    }

    @Test
    fun isValidInvalidProfile() {
        val vm1 = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile(id = "invalid_1"))
        val vm2 = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile(id = "invalid_2"))

        // Wait for the validators to debounce
        Thread.sleep(400)

        assertThat("Invalid profiles are not valid", !vm1.isValid())
        assertThat("Invalid profiles are not valid", !vm2.isValid())
    }

    @Test
    fun isValidValid() {
        val vm1 = ProfileEditorViewModel("tag", log, ctx, pref, Gson(), Profile(id = "valid_1"))

        // Wait for the validators to debounce
        Thread.sleep(400)

        assertThat("Valid profiles are valid", vm1.isValid())
    }

    @Test
    fun save() {

    }

    @Test
    fun refreshValidity() {

    }

    @Test
    fun toggleCollapseSection() {

    }

}