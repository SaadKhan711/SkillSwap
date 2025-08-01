import { useState } from 'react';

export default function RecommendationPanel({ onRecommend, recommendations, onDismiss }) {
    return (
        <div className="bg-gray-800/50 p-6 rounded-lg shadow-lg border border-gray-700 sticky top-8">
            <h2 className="text-2xl font-semibold mb-4">Get My Recommendations</h2>
            <button onClick={onRecommend} className="w-full bg-purple-600 hover:bg-purple-700 p-3 rounded font-bold text-lg flex items-center justify-center gap-2">
                <span className="material-symbols-outlined">auto_awesome</span> Find Recommendations
            </button>
            <hr className="my-6 border-gray-700" />
            <h3 className="text-xl font-semibold mb-4">Recommended For You:</h3>
            <div className="space-y-3 max-h-96 overflow-y-auto pr-2">
                {recommendations.length > 0 ? recommendations.map(rec => (
                    <div key={rec.offering.id} className={`bg-gradient-to-r from-purple-900/50 to-indigo-900/50 p-4 rounded-lg border border-purple-700/50 transition-all hover:border-purple-500 hover:scale-105 flex justify-between items-center`}>
                        <div><p className="font-semibold text-lg">{rec.offering.skill.name}</p><p className="text-sm text-gray-400">Offered by <span className="font-medium text-indigo-300">{rec.offering.user.username}</span></p></div>
                        <div className="flex items-center gap-4">
                            <div className="text-right"><div className="text-sm font-bold text-purple-300">Match Score</div><div className="text-2xl font-bold text-white">{rec.score.toFixed(0)}</div></div>
                            <button onClick={() => onDismiss(rec.offering.id)} className="text-gray-500 hover:text-red-400 p-1 rounded-full hover:bg-red-500/10"><span className="material-symbols-outlined">close</span></button>
                        </div>
                    </div>
                )) : <p className="text-gray-500 text-center py-4 italic">Click the button to see magic happen.</p>}
            </div>
        </div>
    );
}
