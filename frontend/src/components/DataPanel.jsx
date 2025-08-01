export default function DataPanel({ title, items, type, currentUser, onMarkAsComplete }) {
    if (!Array.isArray(items)) return null;
    return (
        <div className="bg-gray-800/50 p-6 rounded-lg shadow-lg border border-gray-700">
            <h2 className="text-2xl font-semibold mb-4">{title}</h2>
            <div className="space-y-3 max-h-96 overflow-y-auto pr-2">
                {items.length > 0 ? items.map(item => (
                    <div key={item.id} className="bg-gray-900 p-3 rounded-md flex justify-between items-center transition-transform hover:scale-105">
                        <div>
                            <span className="font-bold text-indigo-400">{item.username || '...'}</span>
                            <span className="text-gray-400"> {type === 'offering' ? 'offers' : 'wants'} </span>
                            <span className="font-semibold text-gray-200">{item.skillName || '...'}</span>
                            {type === 'request' && item.status === 'COMPLETED' && <span className="ml-2 text-xs font-semibold bg-green-500/20 text-green-300 px-2 py-1 rounded-full">Completed</span>}
                        </div>
                        {type === 'request' && item.userId === currentUser?.id && item.status === 'PENDING' && (
                            <button onClick={() => onMarkAsComplete(item.id)} className="bg-green-600 hover:bg-green-700 text-white text-xs font-bold py-1 px-3 rounded-full transition-colors">
                                Mark Complete
                            </button>
                        )}
                    </div>
                )) : <p className="text-gray-500 italic text-center py-4">The marketplace is quiet...</p>}
            </div>
        </div>
    );
}
