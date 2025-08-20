from a1.context.app_state import AppState

# signleton for global state. Required if not each obj will contain new data
global_app_state = AppState()