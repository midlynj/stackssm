import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {persistor, store} from "./redux/store";
import {Provider} from "react-redux"
import { PersistGate } from 'redux-persist/integration/react';
import { QueryClient, QueryClientProvider } from "react-query";



const root = ReactDOM.createRoot(document.getElementById('root'));
const queryClient = new QueryClient();

root.render(
  <React.StrictMode>
      <QueryClientProvider client={queryClient}>
          <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                  <App />
            </PersistGate>
        </Provider>
      </QueryClientProvider>
  </React.StrictMode>
);
