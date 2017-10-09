'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import Logo from './components/Logo';
import Datum from './components/Datum';
import { LocaleProvider } from 'antd';
import enUS from 'antd/lib/locale-provider/en_US';



ReactDOM.render(
    <LocaleProvider locale={enUS}>
        <h1>
            <Logo />
            <Datum/>
        </h1>
    </LocaleProvider>,
    document.getElementById('app')
);