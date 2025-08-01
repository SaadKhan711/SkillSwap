import { useState } from 'react';
import { api } from '../api';

export default function LoginScreen({ onLogin }) {
    const [isRegister, setIsRegister] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const endpoint = isRegister ? '/auth/register' : '/auth/login';
            const user = await api.post(endpoint, { username, password });
            onLogin(user);
        } catch (err) {
            const errorMessage = typeof err === 'string' ? err : (err.message || 'An error occurred.');
            setError(errorMessage);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-900 p-4">
            <div className="w-full max-w-md bg-gray-800/50 p-8 rounded-lg shadow-lg border border-gray-700">
                <h1 className="text-3xl font-bold text-center mb-2 text-transparent bg-clip-text bg-gradient-to-r from-indigo-400 to-purple-500">SkillSwap</h1>
                <p className="text-center text-gray-400 mb-8">{isRegister ? 'Create a new account' : 'Welcome back!'}</p>
                <form onSubmit={handleSubmit} className="space-y-6">
                    <input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Username" required className="w-full bg-gray-700 p-3 rounded border border-gray-600 focus:ring-2 focus:ring-indigo-500" />
                    <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" required className="w-full bg-gray-700 p-3 rounded border border-gray-600 focus:ring-2 focus:ring-indigo-500" />
                    {error && <p className="text-red-400 text-sm text-center">{error}</p>}
                    <button type="submit" className="w-full bg-indigo-600 hover:bg-indigo-700 p-3 rounded font-bold text-lg">{isRegister ? 'Register' : 'Login'}</button>
                </form>
                <p className="text-center text-sm text-gray-500 mt-6">
                    {isRegister ? 'Already have an account?' : "Don't have an account?"}
                    <button onClick={() => setIsRegister(!isRegister)} className="font-semibold text-indigo-400 hover:underline ml-1">
                        {isRegister ? 'Login' : 'Register'}
                    </button>
                </p>
            </div>
        </div>
    );
}
