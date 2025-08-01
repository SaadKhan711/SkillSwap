import { useState } from 'react';
import { api } from '../api';

export default function SkillPathPanel() {
    const [goal, setGoal] = useState('');
    const [path, setPath] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const handleGeneratePath = async (e) => {
        e.preventDefault();
        if (!goal.trim()) return;
        setIsLoading(true);
        try {
            const results = await api.get(`/skill-path?goal=${encodeURIComponent(goal)}`);
            setPath(results);
        } catch (err) {
            console.error("Failed to generate skill path:", err);
        } finally { setIsLoading(false); }
    };

    return (
        <div className="bg-gray-800/50 p-6 rounded-lg shadow-lg border border-gray-700 col-span-full">
            <h2 className="text-2xl font-semibold mb-4">✨ AI Career Path Generator (Coming Soon)</h2>
            <p className="text-gray-400">This feature is temporarily disabled while we focus on core functionality.</p>
        </div>
    );
}