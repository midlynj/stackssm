import { configureStore } from "@reduxjs/toolkit";
import messageReducer from  "./message"
import { persistStore, persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import reducer from "./auth"

const persistConfig = {
    key: "root", // Key for identifying the persisted data
    storage,
    // Use your preferred storage engine here
    // Other configuration options, if needed
};

const persistedReducer = persistReducer(persistConfig, reducer);

const store = configureStore({
    reducer: {
        persistedReducer,
            message: messageReducer,
    }
    // Optional: Configure middleware, devtools, and other options here
});

const persistor = persistStore(store);

export { store, persistor };
