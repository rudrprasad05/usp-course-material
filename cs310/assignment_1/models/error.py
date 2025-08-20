
'''
used to handle errors in code
'''
class Error(): 
    def __init__(self, error_code: int, error_message: str):
        self.error_code = error_code
        self.error_message = error_message

    def __repr__(self):
        return f"Error(code={self.error_code}, message={self.error_message})"

    def to_dict(self):
        return {
            "error_code": self.error_code,
            "error_message": self.error_message
        }

    @staticmethod
    def from_dict(data):
        return Error(data["error_code"], data["error_message"])
