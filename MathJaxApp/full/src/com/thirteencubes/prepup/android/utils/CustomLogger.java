package com.thirteencubes.prepup.android.utils;

/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2014, FrostWire(R). All rights reserved.
 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.logging.Level;

/**
 * 
 * @author gubatron
 * @author aldenml
 *
 */
public final class CustomLogger {

    private final java.util.logging.Logger jul;

    CustomLogger(java.util.logging.Logger jul) {
        this.jul = jul;
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(java.util.logging.Logger.getLogger(clazz.getName()));
    }

    public void info(String msg) {
        jul.info(msg);
    }

    public void info(String msg, Throwable e) {
        jul.log(Level.INFO, msg, e);
    }

    public void warn(String msg) {
        jul.info(msg);
    }

    public void warn(String msg, Throwable e) {
        jul.log(Level.INFO, msg, e);
    }

    public void error(String msg) {
        jul.info(msg);
    }

    public void error(String msg, Throwable e) {
        jul.log(Level.INFO, msg, e);
    }

    public void debug(String msg) {
        jul.info(msg);
    }

    public void debug(String msg, Throwable e) {
        jul.log(Level.INFO, msg, e);
    }
}