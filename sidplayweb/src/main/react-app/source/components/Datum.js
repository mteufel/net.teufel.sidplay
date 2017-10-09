import React from 'react';
import { DatePicker, message } from 'antd';

import 'antd/dist/antd.css';

class Datum extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            date: '',
        };
    }
    handleChange(date) {
        message.info('Selected Date: ' + date.toString());
        this.setState({ date });
    }
    render() {
        return (
            <div style={{ width: 200, marginRight: 'auto', marginLeft: 'auto'}}>
                <DatePicker onChange={value => this.handleChange(value)} />
            </div>
        );
    }
}

export default Datum