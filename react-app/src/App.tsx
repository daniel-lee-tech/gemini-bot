import './App.css';

import {useState} from 'react';
import Button from '@mui/material/Button';

function App() {
    const [buttonText, setButtonText] = useState("Hello");

    return <Button onClick={() => setButtonText("clicked at" + Date.now())} variant="contained">{buttonText}</Button>;
}

export { App };
