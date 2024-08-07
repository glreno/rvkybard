package com.rfacad.rvkybard.interfaces;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public interface AuthConfigI
{
    /**
     * @return true if Authentication should only permit one login at a time
     */
    boolean isSingleLoginMode();

    void setSingleLoginMode(boolean b);
}
